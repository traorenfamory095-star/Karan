package com.example.karan.viewmodels

import com.example.karan.data.repository.RevisionRepository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.karan.models.Question
import com.example.karan.models.SessionQuiz
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * État du quiz exposé au QuizScreen.
 */
sealed interface QuizUiState {

    object Loading : QuizUiState

    /** Aucune question disponible pour cet examen / cette matière. */
    object Empty : QuizUiState

    data class Error(val message: String) : QuizUiState

    /** Quiz en cours : une question à la fois. */
    data class Ready(
        val question: Question,
        val indexActuel: Int,
        val totalQuestions: Int,
        val reponseSelectionnee: String?,   // "A", "B", "C" ou "D" ; null si pas encore répondu
        val correctionAffichee: Boolean,    // true dès qu'une réponse est choisie
        val estCorrecte: Boolean?,          // null tant qu'il n'y a pas de réponse
        val tempsRestantSecondes: Int,
        val score: Int
    ) : QuizUiState {
        val estDerniereQuestion: Boolean get() = indexActuel == totalQuestions - 1
        val progression: Float get() = (indexActuel + 1).toFloat() / totalQuestions
    }

    /** Quiz terminé : infos pour l'écran Résultat et pour SessionQuiz. */
    data class Finished(
        val score: Int,
        val totalQuestions: Int,
        val pourcentage: Int,
        val dureeSecondes: Int,
        val tempsEcoule: Boolean,
        val questionsErronees: List<Question>,       // erreurs à revoir
        val reponsesDonnees: Map<Long, String>,      // id de la question -> lettre choisie
        val sessionEnregistree: Boolean = false
    ) : QuizUiState
}

class QuizViewModel(
    private val repository: RevisionRepository,
    private val examen: String,              // "BAC" ou "BEPC"
    private val matiereId: Long,
    private val nombreQuestions: Int,
    private val dureeTotaleSecondes: Int = nombreQuestions * 30
) : ViewModel() {

    private val _uiState = MutableStateFlow<QuizUiState>(QuizUiState.Loading)
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    // --- Données internes du quiz en cours ---
    private var questions: List<Question> = emptyList()
    private var indexActuel = 0
    private var reponseSelectionnee: String? = null
    private var score = 0
    private var tempsRestant = dureeTotaleSecondes
    private var termine = false
    private val erreurs = mutableListOf<Question>()
    private val reponsesDonnees = mutableMapOf<Long, String>()
    private var chronoJob: Job? = null

    init {
        require(nombreQuestions > 0) { "nombreQuestions doit être > 0" }
        chargerQuiz()
    }

    // ------------------------------------------------------------
    // CHARGEMENT
    // ------------------------------------------------------------
    fun chargerQuiz() {
        chronoJob?.cancel()
        _uiState.value = QuizUiState.Loading
        viewModelScope.launch {
            try {
                val chargees = repository
                    .getQuestions(examen, matiereId, nombreQuestions)
                    .take(nombreQuestions) // gère aussi "moins de questions que demandé"

                if (chargees.isEmpty()) {
                    _uiState.value = QuizUiState.Empty
                    return@launch
                }
                reinitialiser(chargees)
                demarrerChrono()
                publierEtat()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = QuizUiState.Error(
                    e.message ?: "Impossible de charger les questions"
                )
            }
        }
    }

    private fun reinitialiser(nouvellesQuestions: List<Question>) {
        questions = nouvellesQuestions
        indexActuel = 0
        reponseSelectionnee = null
        score = 0
        tempsRestant = dureeTotaleSecondes
        termine = false
        erreurs.clear()
        reponsesDonnees.clear()
    }

    // ------------------------------------------------------------
    // RÉPONSES
    // ------------------------------------------------------------
    fun selectionnerReponse(choix: String) {
        if (termine || questions.isEmpty()) return
        if (choix.isBlank()) return                    // réponse vide
        if (reponseSelectionnee != null) return        // déjà répondu : on bloque

        val question = questions[indexActuel]
        reponseSelectionnee = choix
        reponsesDonnees[question.id] = choix

        if (estBonneReponse(question, choix)) {
            score++
        } else {
            erreurs.add(question)                      // erreur à revoir
        }
        publierEtat()
    }

    private fun estBonneReponse(question: Question, choix: String): Boolean =
        choix.trim().equals(question.reponseCorrecte.trim(), ignoreCase = true)

    // ------------------------------------------------------------
    // NAVIGATION DANS LE QUIZ
    // ------------------------------------------------------------
    fun questionSuivante() {
        if (termine || questions.isEmpty()) return
        if (reponseSelectionnee == null) return        // il faut répondre avant de continuer

        if (indexActuel >= questions.size - 1) {
            terminerQuiz(tempsEcoule = false)
        } else {
            indexActuel++
            reponseSelectionnee = null
            publierEtat()
        }
    }

    // ------------------------------------------------------------
    // CHRONOMÈTRE
    // ------------------------------------------------------------
    private fun demarrerChrono() {
        chronoJob?.cancel()
        chronoJob = viewModelScope.launch {
            while (tempsRestant > 0) {
                delay(1_000)
                tempsRestant--
                publierEtat()
            }
            terminerQuiz(tempsEcoule = true)
        }
    }

    // ------------------------------------------------------------
    // FIN DU QUIZ
    // ------------------------------------------------------------
    private fun terminerQuiz(tempsEcoule: Boolean) {
        if (termine) return
        termine = true
        chronoJob?.cancel()

        val total = questions.size
        val duree = dureeTotaleSecondes - tempsRestant
        val pourcentage = if (total == 0) 0 else (score * 100) / total

        val resultat = QuizUiState.Finished(
            score = score,
            totalQuestions = total,
            pourcentage = pourcentage,
            dureeSecondes = duree,
            tempsEcoule = tempsEcoule,
            questionsErronees = erreurs.toList(),
            reponsesDonnees = reponsesDonnees.toMap()
        )
        _uiState.value = resultat

        enregistrerSession(resultat)
    }

    private fun enregistrerSession(resultat: QuizUiState.Finished) {
        viewModelScope.launch {
            try {
                repository.saveSession(
                    SessionQuiz(
                        matiereId = matiereId,
                        date = System.currentTimeMillis(),
                        score = resultat.score,
                        total = resultat.totalQuestions,
                        dureeSecondes = resultat.dureeSecondes
                    )
                )
                _uiState.value = resultat.copy(sessionEnregistree = true)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                // La session n'a pas pu être sauvegardée ; l'écran Résultat reste affiché.
                _uiState.value = resultat.copy(sessionEnregistree = false)
            }
        }
    }

    // ------------------------------------------------------------
    // ÉTAT
    // ------------------------------------------------------------
    private fun publierEtat() {
        if (termine || questions.isEmpty()) return
        val question = questions[indexActuel]
        val choix = reponseSelectionnee
        _uiState.value = QuizUiState.Ready(
            question = question,
            indexActuel = indexActuel,
            totalQuestions = questions.size,
            reponseSelectionnee = choix,
            correctionAffichee = choix != null,
            estCorrecte = choix?.let { estBonneReponse(question, it) },
            tempsRestantSecondes = tempsRestant,
            score = score
        )
    }

    // ------------------------------------------------------------
    // FACTORY (pour créer le ViewModel avec ses paramètres)
    // ------------------------------------------------------------
    class Factory(
        private val repository: RevisionRepository,
        private val examen: String,
        private val matiereId: Long,
        private val nombreQuestions: Int,
        private val dureeTotaleSecondes: Int = nombreQuestions * 30
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            QuizViewModel(
                repository, examen, matiereId, nombreQuestions, dureeTotaleSecondes
            ) as T
    }
}