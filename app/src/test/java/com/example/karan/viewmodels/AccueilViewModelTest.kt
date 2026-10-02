package com.example.karan.viewmodels

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AccueilViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val repository = FakeRepository()

    private val maths = uneMatiere(1L, "Mathématiques", "BAC")
    private val physique = uneMatiere(3L, "Physique", "BAC")
    private val francais = uneMatiere(2L, "Français", "BEPC")

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository.matieres = listOf(maths, physique, francais)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun creerViewModel(examen: String = "BAC"): AccueilViewModel {
        val vm = AccueilViewModel(repository, examen)
        dispatcher.scheduler.runCurrent()
        return vm
    }

    // ---------- Examen ----------

    @Test
    fun selectionBac_neMontreQueLesSessionsBac() {
        repository.sessions.add(uneSession(matiereId = 1L, score = 8, date = 10L))
        repository.sessions.add(uneSession(matiereId = 2L, score = 5, date = 20L)) // BEPC

        val etat = creerViewModel("BAC").uiState.value

        assertEquals("BAC", etat.examen)
        assertEquals(1, etat.dernieresSessions.size)
        assertEquals("Mathématiques", etat.dernieresSessions.first().matiere)
    }

    @Test
    fun selectionBepc_neMontreQueLesSessionsBepc() {
        repository.sessions.add(uneSession(matiereId = 1L, score = 8, date = 10L)) // BAC
        repository.sessions.add(uneSession(matiereId = 2L, score = 5, date = 20L))
        val vm = creerViewModel("BAC")

        vm.selectionnerExamen("BEPC")
        dispatcher.scheduler.runCurrent()

        val etat = vm.uiState.value
        assertEquals("BEPC", etat.examen)
        assertEquals(1, etat.dernieresSessions.size)
        assertEquals("Français", etat.dernieresSessions.first().matiere)
    }

    // ---------- Sessions et progression ----------

    @Test
    fun dernieresSessions_sontLimiteesACinqEtTriees() {
        (1L..7L).forEach { jour ->
            repository.sessions.add(uneSession(matiereId = 1L, score = 5, date = jour))
        }

        val etat = creerViewModel().uiState.value

        assertEquals(5, etat.dernieresSessions.size)
        assertEquals(7L, etat.dernieresSessions.first().date) // la plus récente d'abord
    }

    @Test
    fun progression_calculeNombreEtMoyenne() {
        repository.sessions.add(uneSession(matiereId = 1L, score = 8, date = 10L)) // 80 %
        repository.sessions.add(uneSession(matiereId = 1L, score = 6, date = 20L)) // 60 %

        val progression = creerViewModel().uiState.value.progression

        assertEquals(2, progression.nombreQuiz)
        assertEquals(70, progression.reussiteMoyenne)
    }

    @Test
    fun derniereMatiere_estCelleDeLaSessionLaPlusRecente() {
        repository.sessions.add(uneSession(matiereId = 1L, score = 8, date = 10L))
        repository.sessions.add(uneSession(matiereId = 3L, score = 6, date = 20L))

        assertEquals("Physique", creerViewModel().uiState.value.derniereMatiere)
    }

    @Test
    fun sessionResume_contientLePourcentage() {
        repository.sessions.add(uneSession(matiereId = 1L, score = 9, total = 10, date = 10L))

        val resume = creerViewModel().uiState.value.dernieresSessions.first()

        assertEquals(9, resume.score)
        assertEquals(10, resume.total)
        assertEquals(90, resume.pourcentage)
    }

    // ---------- États vides ----------

    @Test
    fun aucuneSession_afficheLeMessageVide() {
        val etat = creerViewModel().uiState.value

        assertTrue(etat.aucuneSession)
        assertEquals(MSG_AUCUNE_DONNEE, etat.messageVide)
        assertNull(etat.derniereMatiere)
        assertEquals(0, etat.progression.nombreQuiz)
    }

    @Test
    fun aucuneErreurARevoir_afficheLeMessage() {
        repository.nombreErreursARevoir = 0

        val etat = creerViewModel().uiState.value

        assertTrue(etat.aucuneErreurARevoir)
        assertEquals(MSG_AUCUNE_ERREUR_A_REVOIR, etat.messageAucuneErreur)
    }

    @Test
    fun erreursARevoir_sontComptees() {
        repository.nombreErreursARevoir = 4

        val etat = creerViewModel().uiState.value

        assertEquals(4, etat.nombreErreursARevoir)
        assertFalse(etat.aucuneErreurARevoir)
    }

    // ---------- Erreurs ----------

    @Test
    fun erreurDeLecture_exposeLErreur() {
        repository.erreur = true

        val etat = creerViewModel().uiState.value

        assertNotNull(etat.erreur)
        assertFalse(etat.chargement)
    }
}