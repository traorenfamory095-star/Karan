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

/**
 * Une question à revoir dans l'écran de correction.
 */
data class CorrectionItem(
    val questionId: Long,
    val enonce: String,
    val reponseDonnee: String?,
    val bonneReponse: String,
    val explication: String
)

/**
 * Progression de l'élève dans la matière du quiz.
 */
data class ProgressionMatiere(
    val nombreQuiz: Int,
    val reussiteMoyenne: Int
)

/**
 * État unique exposé à ResultatScreen.
 */
sealed interface ResultatUiState {

    data object Loading : ResultatUiState

    data object Empty : ResultatUiState

    data class Error(
        val message: String
    ) : ResultatUiState

    data class Success(
        val score: Int,
        val totalQuestions: Int,
        val bonnesReponses: Int,
        val mauvaisesReponses: Int,
        val pourcentage: Int,
        val matiere: String,
        val examen: String,
        val dureeSecondes: Int,
        val correction: List<CorrectionItem>,
        val progression: ProgressionMatiere
    ) : ResultatUiState {

        val correctionDisponible: Boolean
            get() = correction.isNotEmpty()
    }
}

/**
 * Transforme le résultat terminé du quiz
 * en éléments utilisables par ResultatScreen.
 */
fun QuizUiState.Finished.toCorrectionItems():
        List<CorrectionItem> {

    return questionsErronees.map { question ->

        CorrectionItem(
            questionId = question.id,
            enonce = question.enonce,
            reponseDonnee =
                reponsesDonnees[question.id],
            bonneReponse =
                question.reponseCorrecte,
            explication =
                question.explication
        )
    }
}

class ResultatViewModel(
    private val repository: RevisionRepository,
    private val correction: List<CorrectionItem> = emptyList()
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ResultatUiState>(
            ResultatUiState.Loading
        )

    val uiState: StateFlow<ResultatUiState> =
        _uiState.asStateFlow()

    init {
        charger()
    }

    /**
     * Charge le dernier résultat enregistré.
     *
     * Pour le MVP, on récupère la dernière session.
     * Une évolution future pourra charger une session
     * précise grâce à son identifiant.
     */
    fun charger() {

        _uiState.value =
            ResultatUiState.Loading

        viewModelScope.launch {

            try {

                val session =
                    repository.getDerniereSession()

                if (session == null) {

                    _uiState.value =
                        ResultatUiState.Empty

                    return@launch
                }

                val matiere =
                    repository.getMatiere(
                        session.matiereId
                    )

                val sessionsMatiere =
                    repository.getSessionsByMatiere(
                        session.matiereId
                    )

                val total =
                    session.total.coerceAtLeast(0)

                val bonnesReponses =
                    session.score.coerceIn(
                        minimumValue = 0,
                        maximumValue = total
                    )

                val mauvaisesReponses =
                    (
                            total - bonnesReponses
                            ).coerceAtLeast(0)

                _uiState.value =
                    ResultatUiState.Success(

                        score =
                            bonnesReponses,

                        totalQuestions =
                            total,

                        bonnesReponses =
                            bonnesReponses,

                        mauvaisesReponses =
                            mauvaisesReponses,

                        pourcentage =
                            calculerPourcentage(
                                score = bonnesReponses,
                                total = total
                            ),

                        matiere =
                            matiere?.nom
                                ?: "Matière inconnue",

                        examen =
                            matiere?.examen
                                ?: "Inconnu",

                        dureeSecondes =
                            session.dureeSecondes
                                .coerceAtLeast(0),

                        correction =
                            correction,

                        progression =
                            calculerProgression(
                                sessionsMatiere
                            )
                    )

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                _uiState.value =
                    ResultatUiState.Error(
                        e.message
                            ?: "Impossible de charger le résultat."
                    )
            }
        }
    }

    /**
     * Calcule le pourcentage d'un score.
     */
    private fun calculerPourcentage(
        score: Int,
        total: Int
    ): Int {

        if (total <= 0) {
            return 0
        }

        return (
                score.coerceAtLeast(0) * 100
                ) / total
    }

    /**
     * Calcule la progression moyenne
     * de la matière.
     */
    private fun calculerProgression(
        sessions: List<SessionQuiz>
    ): ProgressionMatiere {

        val pourcentages =
            sessions
                .filter { it.total > 0 }
                .map {
                    calculerPourcentage(
                        score = it.score,
                        total = it.total
                    )
                }

        val moyenne =
            if (pourcentages.isEmpty()) {
                0
            } else {
                pourcentages.average().toInt()
            }

        return ProgressionMatiere(
            nombreQuiz = pourcentages.size,
            reussiteMoyenne = moyenne
        )
    }

    /**
     * Factory permettant d'injecter le Repository
     * et les données de correction.
     */
    class Factory(
        private val repository: RevisionRepository,
        private val correction: List<CorrectionItem> =
            emptyList()
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>
        ): T {

            return ResultatViewModel(
                repository = repository,
                correction = correction
            ) as T
        }
    }
}