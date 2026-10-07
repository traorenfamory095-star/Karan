
package com.example.karan.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.karan.data.repository.RevisionRepository
import com.example.karan.models.SessionQuiz
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.util.Log

const val MSG_AUCUNE_DONNEE = "Aucune donnée pour le moment"
const val MSG_AUCUNE_ERREUR_A_REVOIR =
    "Aucune erreur à revoir pour le moment."

private const val LIMITE_SESSIONS_CHARGEES = 50
private const val NOMBRE_DERNIERES_SESSIONS = 5
private const val EXAMEN_PAR_DEFAUT = "BAC"

/**
 * Représentation simplifiée d'une session
 * pour l'affichage sur l'écran d'accueil.
 */
data class SessionResume(
    val matiere: String,
    val score: Int,
    val total: Int,
    val pourcentage: Int,
    val date: Long
)

/**
 * Progression générale de l'élève
 * pour l'examen sélectionné.
 */
data class ProgressionGenerale(
    val nombreQuiz: Int,
    val reussiteMoyenne: Int
)

/** * Progression d'une matière
 *
 * affichée sur l'écran d'accueil.
 * */
data class ProgressionParMatiere(
    val matiereId: Long,
    val matiere: String,
    val nombreQuiz: Int,
    val reussiteMoyenne: Int
)


/**
 * État unique de l'écran d'accueil.
 */
data class AccueilUiState(
    val chargement: Boolean = true,
    val examen: String = EXAMEN_PAR_DEFAUT,
    val progression: ProgressionGenerale = ProgressionGenerale(
        nombreQuiz = 0,
        reussiteMoyenne = 0
    ),
    val progressionsMatieres: List<ProgressionParMatiere> = emptyList(),
    val dernieresSessions: List<SessionResume> = emptyList(),
    val derniereMatiere: String? = null,
    val nombreErreursARevoir: Int = 0,
    val erreur: String? = null
) {

    val aucuneSession: Boolean
        get() = !chargement &&
                erreur == null &&
                dernieresSessions.isEmpty()

    val aucuneErreurARevoir: Boolean
        get() = !chargement &&
                erreur == null &&
                nombreErreursARevoir == 0

    val messageVide: String?
        get() = if (aucuneSession) {
            MSG_AUCUNE_DONNEE
        } else {
            null
        }

    val messageAucuneErreur: String?
        get() = if (aucuneErreurARevoir) {
            MSG_AUCUNE_ERREUR_A_REVOIR
        } else {
            null
        }
}

class AccueilViewModel(
    private val repository: RevisionRepository,
    examenInitial: String = EXAMEN_PAR_DEFAUT
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AccueilUiState(
            examen = examenInitial
        )
    )

    val uiState: StateFlow<AccueilUiState> =
        _uiState.asStateFlow()

    private var chargementJob: Job? = null

    init {
        charger()
    }

    /**
     * Change l'examen sélectionné.
     */
    fun selectionnerExamen(examen: String) {
        if (examen == _uiState.value.examen) return

        _uiState.update {
            it.copy(
                examen = examen
            )
        }

        charger()
    }

    /**
     * Recharge toutes les données nécessaires
     * à l'écran d'accueil.
     */
    fun charger() {
        Log.d("AccueilDebug", "charger() début")
        chargementJob?.cancel()

        _uiState.update {
            it.copy(
                chargement = it.progression.nombreQuiz == 0 &&
                        it.dernieresSessions.isEmpty(),
                erreur = null
            )
        }

        val examen = _uiState.value.examen

        chargementJob = viewModelScope.launch {
            try {
                /*
                 * Les sessions possèdent un matiereId.
                 * On récupère donc les matières de l'examen
                 * afin de pouvoir associer chaque session
                 * au nom de sa matière.
                 */
                val matieres = repository.getMatieres(examen)

                val nomsParId = matieres.associate {
                    it.id to it.nom
                }

                val sessions = repository
                    .getDernieresSessions(LIMITE_SESSIONS_CHARGEES)
                    .filter { session ->
                        session.matiereId in nomsParId
                    }
                    .sortedByDescending { it.date }

                val progressionsMatieres = sessions
                    .filter { it.total > 0 }
                    .groupBy { it.matiereId }
                    .map { (matiereId, sessionsMatiere) ->

                        val pourcentages = sessionsMatiere.map {
                            calculerPourcentage(
                                score = it.score,
                                total = it.total
                            )
                        }

                        val moyenne = pourcentages
                            .average()
                            .toInt()

                        ProgressionParMatiere(
                            matiereId = matiereId,
                            matiere = nomsParId[matiereId] ?: "Matière inconnue",
                            nombreQuiz = sessionsMatiere.size,
                            reussiteMoyenne = moyenne
                        )
                    }
                    .sortedByDescending { it.reussiteMoyenne }

                Log.d("AccueilDebug", "examen = $examen")
                Log.d("AccueilDebug", "matières = $nomsParId")
                Log.d("AccueilDebug", "sessions = $sessions")
                Log.d("AccueilDebug", "nombre sessions = ${sessions.size}")
                Log.d(
                    "AccueilDebug",
                    "progressions matières = $progressionsMatieres"
                )



                val erreursARevoir =
                    repository.countErreursARevoir(examen)

                Log.d("AccueilDebug", "erreurs à revoir ($examen) = $erreursARevoir")

                val dernieresSessions = sessions
                    .take(NOMBRE_DERNIERES_SESSIONS)
                    .map { session ->
                        SessionResume(
                            matiere = nomsParId[session.matiereId]
                                ?: "Matière inconnue",
                            score = session.score,
                            total = session.total,
                            pourcentage = calculerPourcentage(
                                session.score,
                                session.total
                            ),
                            date = session.date
                        )
                    }

                Log.d("AccueilDebug", "charger() fin")
                _uiState.update {
                    it.copy(
                        chargement = false,
                        progression = calculerProgression(sessions),
                        dernieresSessions = dernieresSessions,
                        progressionsMatieres = progressionsMatieres,
                        derniereMatiere = sessions
                            .firstOrNull()
                            ?.let { session ->
                                nomsParId[session.matiereId]
                            },
                        nombreErreursARevoir = erreursARevoir,
                        erreur = null
                    )
                }

            } catch (e: CancellationException) {
                /*
                 * Une annulation de coroutine ne doit pas
                 * être considérée comme une erreur.
                 */
                throw e

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        chargement = false,
                        erreur = e.message
                            ?: "Impossible de charger les données"
                    )
                }
            }
        }
    }

    /**
     * Calcule le pourcentage obtenu à un quiz.
     */
    private fun calculerPourcentage(
        score: Int,
        total: Int
    ): Int {
        if (total <= 0) return 0

        return (score * 100) / total
    }

    /**
     * Calcule la progression générale
     * à partir des sessions réalisées.
     */
    private fun calculerProgression(
        sessions: List<SessionQuiz>
    ): ProgressionGenerale {

        val pourcentages = sessions
            .filter { it.total > 0 }
            .map {
                calculerPourcentage(
                    score = it.score,
                    total = it.total
                )
            }

        val moyenne = if (pourcentages.isEmpty()) {
            0
        } else {
            pourcentages.average().toInt()
        }

        return ProgressionGenerale(
            nombreQuiz = sessions.size,
            reussiteMoyenne = moyenne
        )
    }

    /**
     * Factory permettant de créer le ViewModel
     * avec le Repository injecté.
     */
    class Factory(
        private val repository: RevisionRepository,
        private val examenInitial: String = EXAMEN_PAR_DEFAUT
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>
        ): T {
            return AccueilViewModel(
                repository = repository,
                examenInitial = examenInitial
            ) as T
        }
    }
}

