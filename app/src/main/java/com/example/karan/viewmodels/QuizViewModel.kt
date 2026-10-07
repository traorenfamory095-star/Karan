package com.example.karan.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.karan.data.repository.RevisionRepository
import com.example.karan.models.ErreurQuestion
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

    /**
     * Chargement des questions.
     */
    data object Loading : QuizUiState

    /**
     * Aucune question disponible.
     */
    data object Empty : QuizUiState

    /**
     * Une erreur est survenue pendant le chargement.
     */
    data class Error(
        val message: String
    ) : QuizUiState

    /**
     * Quiz en cours.
     */
    data class Ready(
        val question: Question,
        val indexActuel: Int,
        val totalQuestions: Int,
        val reponseSelectionnee: String?,
        val correctionAffichee: Boolean,
        val estCorrecte: Boolean?,
        val tempsRestantSecondes: Int,
        val score: Int
    ) : QuizUiState {

        val estDerniereQuestion: Boolean
            get() = indexActuel == totalQuestions - 1

        val progression: Float
            get() = if (totalQuestions <= 0) {
                0f
            } else {
                (indexActuel + 1).toFloat() / totalQuestions
            }
    }

    /**
     * Quiz terminé.
     */
    data class Finished(
        val score: Int,
        val totalQuestions: Int,
        val pourcentage: Int,
        val dureeSecondes: Int,
        val tempsEcoule: Boolean,
        val questionsErronees: List<Question>,
        val reponsesDonnees: Map<Long, String>,
        val sessionEnregistree: Boolean = false
    ) : QuizUiState
}

class QuizViewModel(
    private val repository: RevisionRepository,
    private val examen: String,
    private val matiereId: Long,
    private val nombreQuestions: Int,
    private val dureeTotaleSecondes: Int = nombreQuestions * 30
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<QuizUiState>(QuizUiState.Loading)

    val uiState: StateFlow<QuizUiState> =
        _uiState.asStateFlow()

    // ============================================================
    // DONNÉES INTERNES DU QUIZ
    // ============================================================

    private var questions: List<Question> = emptyList()

    private var indexActuel = 0

    private var reponseSelectionnee: String? = null

    private var score = 0

    private var tempsRestant = dureeTotaleSecondes

    private var termine = false

    private val erreurs = mutableListOf<Question>()

    private val reponsesDonnees =
        mutableMapOf<Long, String>()

    private var chronoJob: Job? = null

    init {
        require(nombreQuestions > 0) {
            "nombreQuestions doit être supérieur à 0."
        }

        require(dureeTotaleSecondes > 0) {
            "dureeTotaleSecondes doit être supérieure à 0."
        }

        chargerQuiz()
    }

    // ============================================================
    // CHARGEMENT
    // ============================================================

    /**
     * Charge les questions depuis le Repository.
     */
    fun chargerQuiz() {

        chronoJob?.cancel()

        termine = false

        _uiState.value = QuizUiState.Loading

        viewModelScope.launch {

            try {

                val chargees = repository.getQuestions(
                    examen = examen,
                    matiereId = matiereId,
                    nombreQuestions = nombreQuestions
                )

                if (chargees.isEmpty()) {

                    _uiState.value = QuizUiState.Empty

                    return@launch
                }

                reinitialiser(chargees)

                publierEtat()

                demarrerChrono()

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                _uiState.value = QuizUiState.Error(
                    e.message
                        ?: "Impossible de charger les questions."
                )
            }
        }
    }

    /**
     * Réinitialise complètement le quiz.
     */
    private fun reinitialiser(
        nouvellesQuestions: List<Question>
    ) {

        questions = nouvellesQuestions

        indexActuel = 0

        reponseSelectionnee = null

        score = 0

        tempsRestant = dureeTotaleSecondes

        termine = false

        erreurs.clear()

        reponsesDonnees.clear()
    }

    // ============================================================
    // RÉPONSES
    // ============================================================

    /**
     * Sélectionne une réponse pour la question actuelle.
     *
     * Une question ne peut recevoir qu'une seule réponse.
     */
    fun selectionnerReponse(choix: String) {

        if (termine || questions.isEmpty()) {
            return
        }

        if (reponseSelectionnee != null) {
            return
        }

        val choixNormalise =
            choix.trim().uppercase()

        if (choixNormalise !in setOf("A", "B", "C", "D")) {
            return
        }

        val question = questions[indexActuel]

        reponseSelectionnee = choixNormalise

        reponsesDonnees[question.id] =
            choixNormalise

        if (estBonneReponse(question, choixNormalise)) {

            score++

        } else {

            erreurs.add(question)
            enregistrerErreur(question)
        }

        publierEtat()
    }

    /**
     * Vérifie si une réponse est correcte.
     */
    private fun estBonneReponse(
        question: Question,
        choix: String
    ): Boolean {

        return choix
            .trim()
            .equals(
                question.reponseCorrecte.trim(),
                ignoreCase = true
            )
    }

    // ============================================================
    // NAVIGATION
    // ============================================================

    /**
     * Passe à la question suivante.
     *
     * Une réponse doit obligatoirement être sélectionnée
     * avant de continuer.
     */
    fun questionSuivante() {

        if (termine || questions.isEmpty()) {
            return
        }

        if (reponseSelectionnee == null) {
            return
        }

        if (indexActuel >= questions.lastIndex) {

            terminerQuiz(
                tempsEcoule = false
            )

            return
        }

        indexActuel++

        reponseSelectionnee = null

        publierEtat()
    }

    // ============================================================
    // CHRONOMÈTRE
    // ============================================================

    /**
     * Démarre le compte à rebours.
     */
    private fun demarrerChrono() {

        chronoJob?.cancel()

        chronoJob = viewModelScope.launch {

            while (
                tempsRestant > 0 &&
                !termine
            ) {

                delay(1_000)

                if (termine) {
                    return@launch
                }

                tempsRestant--

                publierEtat()
            }

            if (!termine && tempsRestant <= 0) {

                terminerQuiz(
                    tempsEcoule = true
                )
            }
        }
    }

    private fun enregistrerErreur(question: Question) {
        viewModelScope.launch {
            try {
                repository.saveErreur(
                    ErreurQuestion(
                        questionId = question.id,
                        matiereId = matiereId,
                        examen = examen,
                        date = System.currentTimeMillis()
                    )
                )
                Log.d("QuizDebug", "erreur sauvegardée : ${question.id}")
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("QuizDebug", "échec saveErreur", e)
            }
        }
    }

    // ============================================================
    // FIN DU QUIZ
    // ============================================================

    /**
     * Termine le quiz et prépare les données
     * destinées à ResultatScreen.
     */
    private fun terminerQuiz(
        tempsEcoule: Boolean
    ) {

        if (termine) {
            return
        }

        termine = true

        chronoJob?.cancel()

        val total = questions.size

        val duree = (
                dureeTotaleSecondes - tempsRestant
                ).coerceIn(
                minimumValue = 0,
                maximumValue = dureeTotaleSecondes
            )

        val pourcentage =
            if (total <= 0) {
                0
            } else {
                (score * 100) / total
            }

        val resultat =
            QuizUiState.Finished(
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

    /**
     * Enregistre la session dans Room via le Repository.
     *
     * Une erreur de sauvegarde ne bloque pas l'affichage
     * du résultat du quiz.
     */

    private fun enregistrerSession(
        resultat: QuizUiState.Finished
    ) {
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

                _uiState.value =
                    resultat.copy(
                        sessionEnregistree = true
                    )

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {
                Log.e("QuizDebug", "échec saveSession", e)

                _uiState.value =
                    resultat.copy(
                        sessionEnregistree = false
                    )
            }
        }
    }

    // ============================================================
    // PUBLICATION DE L'ÉTAT
    // ============================================================

    /**
     * Construit l'état Ready à partir
     * des données internes du quiz.
     */
    private fun publierEtat() {

        if (termine || questions.isEmpty()) {
            return
        }

        val question =
            questions[indexActuel]

        val choix =
            reponseSelectionnee

        _uiState.value =
            QuizUiState.Ready(
                question = question,
                indexActuel = indexActuel,
                totalQuestions = questions.size,
                reponseSelectionnee = choix,
                correctionAffichee = choix != null,
                estCorrecte = choix?.let {
                    estBonneReponse(
                        question,
                        it
                    )
                },
                tempsRestantSecondes = tempsRestant,
                score = score
            )
    }

    // ============================================================
    // FACTORY
    // ============================================================

    class Factory(
        private val repository: RevisionRepository,
        private val examen: String,
        private val matiereId: Long,
        private val nombreQuestions: Int,
        private val dureeTotaleSecondes: Int =
            nombreQuestions * 30
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>
        ): T {

            return QuizViewModel(
                repository = repository,
                examen = examen,
                matiereId = matiereId,
                nombreQuestions = nombreQuestions,
                dureeTotaleSecondes = dureeTotaleSecondes
            ) as T
        }
    }
}