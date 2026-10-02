package com.example.karan.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.karan.data.repository.RevisionRepository
import com.example.karan.models.SessionQuiz
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Une question à revoir dans l'écran de correction. */
data class CorrectionItem(
    val questionId: Long,
    val enonce: String,
    val reponseDonnee: String?,     // lettre choisie, null si pas de réponse
    val bonneReponse: String,
    val explication: String
)

/** Progression de l'élève dans la matière du quiz. */
data class ProgressionMatiere(
    val nombreQuiz: Int,
    val reussiteMoyenne: Int        // pourcentage moyen sur tous les quiz de la matière
)

/** État unique exposé à ResultatScreen. */
sealed interface ResultatUiState {

    object Loading : ResultatUiState

    /** "Aucun résultat disponible pour le moment." */
    object Empty : ResultatUiState

    data class Error(val message: String) : ResultatUiState

    data class Success(
        val score: Int,
        val totalQuestions: Int,
        val bonnesReponses: Int,
        val mauvaisesReponses: Int,
        val pourcentage: Int,
        val matiere: String,
        val examen: String,
        val dureeSecondes: Int,
        val correction: List<CorrectionItem>,   // erreurs à revoir (peut être vide)
        val progression: ProgressionMatiere
    ) : ResultatUiState {
        val correctionDisponible: Boolean get() = correction.isNotEmpty()
    }
}

/**
 * Transforme le résultat d'un quiz terminé en liste de questions à revoir.
 * À utiliser par celui qui crée ResultatViewModel.
 */
fun QuizUiState.Finished.toCorrectionItems(): List<CorrectionItem> =
    questionsErronees.map { question ->
        CorrectionItem(
            questionId = question.id,
            enonce = question.enonce,
            reponseDonnee = reponsesDonnees[question.id],
            bonneReponse = question.reponseCorrecte,
            explication = question.explication
        )
    }

class ResultatViewModel(
    private val repository: RevisionRepository,
    private val correction: List<CorrectionItem> = emptyList()
) : ViewModel() {

    private val _uiState = MutableStateFlow<ResultatUiState>(ResultatUiState.Loading)
    val uiState: StateFlow<ResultatUiState> = _uiState.asStateFlow()

    init {
        charger()
    }

    fun charger() {
        _uiState.value = ResultatUiState.Loading
        viewModelScope.launch {
            try {
                val session = repository.getDerniereSession()
                if (session == null) {
                    _uiState.value = ResultatUiState.Empty
                    return@launch
                }

                val matiere = repository.getMatiere(session.matiereId)
                val sessionsMatiere = repository.getSessionsByMatiere(session.matiereId)

                _uiState.value = ResultatUiState.Success(
                    score = session.score,
                    totalQuestions = session.total,
                    bonnesReponses = session.score,
                    mauvaisesReponses = (session.total - session.score).coerceAtLeast(0),
                    pourcentage = pourcentage(session.score, session.total),
                    matiere = matiere?.nom ?: "Matière inconnue",
                    examen = matiere?.examen ?: "",
                    dureeSecondes = session.dureeSecondes,
                    correction = correction,
                    progression = calculerProgression(sessionsMatiere)
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = ResultatUiState.Error(
                    e.message ?: "Impossible de charger le résultat"
                )
            }
        }
    }

    private fun pourcentage(score: Int, total: Int): Int =
        if (total <= 0) 0 else (score * 100) / total

    private fun calculerProgression(sessions: List<SessionQuiz>): ProgressionMatiere {
        val pourcentages = sessions
            .filter { it.total > 0 }
            .map { pourcentage(it.score, it.total) }
        return ProgressionMatiere(
            nombreQuiz = sessions.size,
            reussiteMoyenne = if (pourcentages.isEmpty()) 0 else pourcentages.average().toInt()
        )
    }

    class Factory(
        private val repository: RevisionRepository,
        private val correction: List<CorrectionItem> = emptyList()
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ResultatViewModel(repository, correction) as T
    }
}