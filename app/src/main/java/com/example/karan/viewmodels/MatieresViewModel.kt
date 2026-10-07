package com.example.karan.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.karan.data.repository.RevisionRepository
import com.example.karan.models.Fiche
import com.example.karan.models.Matiere
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val MSG_AUCUNE_MATIERE =
    "Aucune matière disponible pour le moment."

const val MSG_AUCUNE_FICHE =
    "Aucune fiche disponible pour le moment."

private val OPTIONS_NOMBRE_QUESTIONS =
    listOf(5, 10, 20)

/**
 * Paramètres nécessaires pour lancer un quiz.
 *
 * Ces informations seront utilisées par la navigation
 * pour ouvrir QuizScreen.
 */
data class ParametresQuiz(
    val examen: String,
    val matiereId: Long,
    val nombreQuestions: Int
)

/**
 * État unique de MatieresScreen.
 */
data class MatieresUiState(
    val chargement: Boolean = true,
    val examen: String = "BAC",
    val matieres: List<Matiere> = emptyList(),
    val matiereSelectionnee: Matiere? = null,
    val chargementFiches: Boolean = false,
    val fiches: List<Fiche> = emptyList(),
    val nombreQuestions: Int = 10,
    val erreur: String? = null,
    val messageValidation: String? = null,

    /**
     * Non null lorsque les paramètres du quiz
     * sont valides et que la navigation peut commencer.
     */
    val parametresQuiz: ParametresQuiz? = null
) {

    val aucuneMatiere: Boolean
        get() = !chargement &&
                erreur == null &&
                matieres.isEmpty()

    val aucuneFiche: Boolean
        get() = matiereSelectionnee != null &&
                !chargementFiches &&
                erreur == null &&
                fiches.isEmpty()

    val messageMatieresVides: String?
        get() = if (aucuneMatiere) {
            MSG_AUCUNE_MATIERE
        } else {
            null
        }

    val messageFichesVides: String?
        get() = if (aucuneFiche) {
            MSG_AUCUNE_FICHE
        } else {
            null
        }
}


class MatieresViewModel(
    private val repository: RevisionRepository,
    examenInitial: String = "BAC"
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MatieresUiState(
            examen = examenInitial
        )
    )

    val uiState: StateFlow<MatieresUiState> =
        _uiState.asStateFlow()

    private var matieresJob: Job? = null
    private var fichesJob: Job? = null

    init {
        chargerMatieres()
    }

    // ============================================================
    // EXAMEN ET MATIÈRES
    // ============================================================

    /**
     * Change l'examen sélectionné.
     */
    fun selectionnerExamen(examen: String) {

        if (examen == _uiState.value.examen) {
            return
        }

        fichesJob?.cancel()

        _uiState.update {
            it.copy(
                examen = examen,
                matiereSelectionnee = null,
                fiches = emptyList(),
                chargementFiches = false,
                messageValidation = null,
                parametresQuiz = null,
                erreur = null
            )
        }

        chargerMatieres()
    }

    /**
     * Charge les matières correspondant à l'examen sélectionné.
     */
    fun chargerMatieres() {

        matieresJob?.cancel()

        _uiState.update {
            it.copy(
                chargement = true,
                erreur = null,
                matieres = emptyList()
            )
        }

        val examen = _uiState.value.examen

        matieresJob = viewModelScope.launch {

            try {

                val liste = repository.getMatieres(examen)

                _uiState.update {
                    it.copy(
                        chargement = false,
                        matieres = liste,
                        erreur = null
                    )
                }

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        chargement = false,
                        erreur = e.message
                            ?: "Impossible de charger les matières"
                    )
                }
            }
        }
    }

    // ============================================================
    // MATIÈRE ET FICHES
    // ============================================================

    /**
     * Sélectionne une matière et charge ses fiches.
     */
    fun selectionnerMatiere(matiere: Matiere) {

        fichesJob?.cancel()

        _uiState.update {
            it.copy(
                matiereSelectionnee = matiere,
                fiches = emptyList(),
                chargementFiches = true,
                erreur = null,
                messageValidation = null,
                parametresQuiz = null
            )
        }

        fichesJob = viewModelScope.launch {

            try {

                val liste = repository.getFiches(matiere.id)

                _uiState.update {
                    it.copy(
                        chargementFiches = false,
                        fiches = liste,
                        erreur = null
                    )
                }

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        chargementFiches = false,
                        erreur = e.message
                            ?: "Impossible de charger les fiches"
                    )
                }
            }
        }
    }

    // ============================================================
    // NOMBRE DE QUESTIONS
    // ============================================================

    /**
     * Modifie le nombre de questions du quiz.
     */
    fun selectionnerNombreQuestions(nombre: Int) {

        if (nombre !in OPTIONS_NOMBRE_QUESTIONS) {

            _uiState.update {
                it.copy(
                    messageValidation =
                        "Nombre de questions invalide."
                )
            }

            return
        }

        _uiState.update {
            it.copy(
                nombreQuestions = nombre,
                messageValidation = null,
                parametresQuiz = null
            )
        }
    }

    // ============================================================
    // LANCEMENT DU QUIZ
    // ============================================================

    /**
     * Vérifie que les paramètres du quiz sont valides
     * avant de demander à l'UI de naviguer vers QuizScreen.
     */
    fun preparerQuiz() {

        val etat = _uiState.value
        val matiere = etat.matiereSelectionnee

        if (matiere == null) {

            _uiState.update {
                it.copy(
                    messageValidation =
                        "Veuillez choisir une matière avant de commencer."
                )
            }

            return
        }

        if (etat.nombreQuestions !in OPTIONS_NOMBRE_QUESTIONS) {

            _uiState.update {
                it.copy(
                    messageValidation =
                        "Nombre de questions invalide."
                )
            }

            return
        }

        viewModelScope.launch {

            try {

                val disponibles = repository.countQuestions(
                    etat.examen,
                    matiere.id
                )

                when {

                    disponibles <= 0 -> {

                        _uiState.update {
                            it.copy(
                                messageValidation =
                                    "Aucune question disponible pour cette matière pour le moment."
                            )
                        }
                    }

                    disponibles < etat.nombreQuestions -> {

                        _uiState.update {
                            it.copy(
                                messageValidation =
                                    "Seulement $disponibles questions disponibles. " +
                                            "Choisissez un nombre plus petit."
                            )
                        }
                    }

                    else -> {

                        _uiState.update {
                            it.copy(
                                messageValidation = null,
                                parametresQuiz = ParametresQuiz(
                                    examen = etat.examen,
                                    matiereId = matiere.id,
                                    nombreQuestions =
                                        etat.nombreQuestions
                                )
                            )
                        }
                    }
                }

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        messageValidation =
                            e.message
                                ?: "Impossible de vérifier les questions."
                    )
                }
            }
        }
    }

    /**
     * À appeler après que la navigation vers QuizScreen
     * a été effectuée.
     */
    fun consommerLancement() {

        _uiState.update {
            it.copy(
                parametresQuiz = null
            )
        }
    }

    /**
     * Efface le message de validation actuel.
     */
    fun effacerMessage() {

        _uiState.update {
            it.copy(
                messageValidation = null
            )
        }
    }

    // ============================================================
    // FACTORY
    // ============================================================

    class Factory(
        private val repository: RevisionRepository,
        private val examenInitial: String = "BAC"
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>
        ): T {

            return MatieresViewModel(
                repository = repository,
                examenInitial = examenInitial
            ) as T
        }
    }
}