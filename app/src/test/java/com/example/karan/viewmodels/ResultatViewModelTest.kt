package com.example.karan.viewmodels

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ResultatViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val repository = FakeRepository()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun creerViewModel(correction: List<CorrectionItem> = emptyList()): ResultatViewModel {
        val vm = ResultatViewModel(repository, correction)
        dispatcher.scheduler.runCurrent()
        return vm
    }

    private fun ResultatViewModel.succes() = uiState.value as ResultatUiState.Success

    @Test
    fun aucuneSession_donneEtatEmpty() {
        val vm = creerViewModel()

        assertTrue(vm.uiState.value is ResultatUiState.Empty)
    }

    @Test
    fun session_afficheScoreEtPourcentage() {
        repository.matieres = listOf(uneMatiere(1L, "Mathématiques", "BAC"))
        repository.sessions.add(uneSession(matiereId = 1L, score = 8, total = 10, dureeSecondes = 120))

        val etat = creerViewModel().succes()

        assertEquals(8, etat.score)
        assertEquals(10, etat.totalQuestions)
        assertEquals(80, etat.pourcentage)
        assertEquals(120, etat.dureeSecondes)
        assertEquals("Mathématiques", etat.matiere)
        assertEquals("BAC", etat.examen)
    }

    @Test
    fun session_calculeBonnesEtMauvaisesReponses() {
        repository.matieres = listOf(uneMatiere(1L, "Mathématiques"))
        repository.sessions.add(uneSession(matiereId = 1L, score = 7, total = 10))

        val etat = creerViewModel().succes()

        assertEquals(7, etat.bonnesReponses)
        assertEquals(3, etat.mauvaisesReponses)
    }

    @Test
    fun total_zero_donnePourcentageZero() {
        repository.matieres = listOf(uneMatiere(1L, "Mathématiques"))
        repository.sessions.add(uneSession(matiereId = 1L, score = 0, total = 0))

        assertEquals(0, creerViewModel().succes().pourcentage)
    }

    @Test
    fun matiereInconnue_afficheMatiereInconnue() {
        repository.matieres = emptyList()
        repository.sessions.add(uneSession(matiereId = 99L, score = 5))

        assertEquals("Matière inconnue", creerViewModel().succes().matiere)
    }

    @Test
    fun resultat_utiliseLaDerniereSession() {
        repository.matieres = listOf(uneMatiere(1L, "Mathématiques"))
        repository.sessions.add(uneSession(matiereId = 1L, score = 8, date = 100L))
        repository.sessions.add(uneSession(matiereId = 1L, score = 6, date = 200L))

        assertEquals(6, creerViewModel().succes().score)
    }

    @Test
    fun progression_calculeNombreEtMoyenne() {
        repository.matieres = listOf(uneMatiere(1L, "Mathématiques"))
        repository.sessions.add(uneSession(matiereId = 1L, score = 8, date = 100L)) // 80 %
        repository.sessions.add(uneSession(matiereId = 1L, score = 6, date = 200L)) // 60 %

        val progression = creerViewModel().succes().progression

        assertEquals(2, progression.nombreQuiz)
        assertEquals(70, progression.reussiteMoyenne)
    }

    @Test
    fun correction_estTransmiseALEtat() {
        repository.matieres = listOf(uneMatiere(1L, "Mathématiques"))
        repository.sessions.add(uneSession(matiereId = 1L, score = 9))
        val item = CorrectionItem(
            questionId = 4L,
            enonce = "Question 4",
            reponseDonnee = "B",
            bonneReponse = "A",
            explication = "Explication 4"
        )

        val etat = creerViewModel(correction = listOf(item)).succes()

        assertEquals(listOf(item), etat.correction)
        assertTrue(etat.correctionDisponible)
    }

    @Test
    fun sansCorrection_correctionNonDisponible() {
        repository.matieres = listOf(uneMatiere(1L, "Mathématiques"))
        repository.sessions.add(uneSession(matiereId = 1L, score = 10))

        assertFalse(creerViewModel().succes().correctionDisponible)
    }

    @Test
    fun erreurDeLecture_donneEtatError() {
        repository.erreur = true

        val vm = creerViewModel()

        assertTrue(vm.uiState.value is ResultatUiState.Error)
    }
}