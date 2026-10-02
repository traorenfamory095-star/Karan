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

const val MSG_AUCUNE_DONNEE = "Aucune donnée pour le moment"
const val MSG_AUCUNE_ERREUR_A_REVOIR = "Aucune erreur à revoir pour le moment."

private const val LIMITE_SESSIONS_CHARGEES = 50
private const val NOMBRE_DERNIERES_SESSIONS = 5

/** Une session affichée dans la liste « dernières sessions ». */
data class SessionResume(
    val matiere: String,
    val score: Int,
    val total: Int,
    val pourcentage: Int,
    val date: Long
)

/** Progression générale de l'élève pour l'examen sélectionné. */
data class ProgressionGenerale(
    val nombreQuiz: Int,
    val reussiteMoyenne: Int        // pourcentage moyen sur tous les quiz
)

/** État unique de AccueilScreen. */
data class AccueilUiState(
    val chargement: Boolean = true,
    val examen: String = "BAC",
    val progression: ProgressionGenerale = ProgressionGenerale(0, 0),
    val dernieresSessions: List<SessionResume> = emptyList(),
    val derniereMatiere: String? = null,
    val nombreErreursARevoir: Int = 0,
    val erreur: String? = null
) {
    val aucuneSession: Boolean
        get() = !chargement && erreur == null && dernieresSessions.isEmpty()

    val aucuneErreurARevoir: Boolean
        get() = !chargement && erreur == null && nombreErreursARevoir == 0

    val messageVide: String? get() = if (aucuneSession) MSG_AUCUNE_DONNEE else null
    val messageAucuneErreur: String? get() = if (aucuneErreurARevoir) MSG_AUCUNE_ERREUR_A_REVOIR else null
}

class AccueilViewModel(
    private val repository: RevisionRepository,
    examenInitial: String = "BAC"
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccueilUiState(examen = examenInitial))
    val uiState: StateFlow<AccueilUiState> = _uiState.asStateFlow()

    private var chargementJob: Job? = null

    init {
        charger()
    }

    fun selectionnerExamen(examen: String) {
        if (examen == _uiState.value.examen) return
        _uiState.update { it.copy(examen = examen) }
        charger()
    }

    fun charger() {
        chargementJob?.cancel()
        _uiState.update { it.copy(chargement = true, erreur = null) }
        val examen = _uiState.value.examen

        chargementJob = viewModelScope.launch {
            try {
                // Les sessions ne connaissent que la matière : on passe par les matières de l'examen.
                val matieres = repository.getMatieres(examen)
                val nomsParId = matieres.associate { it.id to it.nom }

                val sessions = repository.getDernieresSessions(LIMITE_SESSIONS_CHARGEES)
                    .filter { it.matiereId in nomsParId }
                    .sortedByDescending { it.date }

                val erreursARevoir = repository.countErreursARevoir(examen)

                _uiState.update {
                    it.copy(
                        chargement = false,
                        progression = calculerProgression(sessions),
                        dernieresSessions = sessions
                            .take(NOMBRE_DERNIERES_SESSIONS)
                            .map { s ->
                                SessionResume(
                                    matiere = nomsParId[s.matiereId] ?: "Matière inconnue",
                                    score = s.score,
                                    total = s.total,
                                    pourcentage = pourcentage(s.score, s.total),
                                    date = s.date
                                )
                            },
                        derniereMatiere = sessions.firstOrNull()?.let { s -> nomsParId[s.matiereId] },
                        nombreErreursARevoir = erreursARevoir
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        chargement = false,
                        erreur = e.message ?: "Impossible de charger les données"
                    )
                }
            }
        }
    }

    private fun pourcentage(score: Int, total: Int): Int =
        if (total <= 0) 0 else (score * 100) / total

    private fun calculerProgression(sessions: List<SessionQuiz>): ProgressionGenerale {
        val pourcentages = sessions
            .filter { it.total > 0 }
            .map { pourcentage(it.score, it.total) }
        return ProgressionGenerale(
            nombreQuiz = sessions.size,
            reussiteMoyenne = if (pourcentages.isEmpty()) 0 else pourcentages.average().toInt()
        )
    }

    class Factory(
        private val repository: RevisionRepository,
        private val examenInitial: String = "BAC"
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            AccueilViewModel(repository, examenInitial) as T
    }
}
