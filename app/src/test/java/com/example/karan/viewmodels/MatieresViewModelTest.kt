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
class MatieresViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val repository = FakeRepository()

    private val maths = uneMatiere(1L, "Mathématiques", "BAC")
    private val francais = uneMatiere(2L, "Français", "BEPC")

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun creerViewModel(examen: String = "BAC"): MatieresViewModel {
        val vm = MatieresViewModel(repository, examen)
        dispatcher.scheduler.runCurrent()
        return vm
    }

    private fun attendre() = dispatcher.scheduler.runCurrent()

    // ---------- Examen et matières ----------

    @Test
    fun chargement_filtreLesMatieresDeLExamenBac() {
        repository.matieres = listOf(maths, francais)

        val etat = creerViewModel("BAC").uiState.value

        assertEquals(listOf(maths), etat.matieres)
        assertFalse(etat.chargement)
    }

    @Test
    fun selectionBepc_afficheLesMatieresBepc() {
        repository.matieres = listOf(maths, francais)
        val vm = creerViewModel("BAC")

        vm.selectionnerExamen("BEPC")
        attendre()

        val etat = vm.uiState.value
        assertEquals("BEPC", etat.examen)
        assertEquals(listOf(francais), etat.matieres)
    }

    @Test
    fun changementDExamen_annuleLaMatiereSelectionnee() {
        repository.matieres = listOf(maths, francais)
        val vm = creerViewModel("BAC")
        vm.selectionnerMatiere(maths)
        attendre()

        vm.selectionnerExamen("BEPC")
        attendre()

        assertNull(vm.uiState.value.matiereSelectionnee)
    }

    @Test
    fun aucuneMatiere_afficheLeMessageVide() {
        repository.matieres = emptyList()

        val etat = creerViewModel().uiState.value

        assertTrue(etat.aucuneMatiere)
        assertEquals(MSG_AUCUNE_MATIERE, etat.messageMatieresVides)
    }

    @Test
    fun erreurDeChargement_exposeLErreur() {
        repository.erreur = true

        val etat = creerViewModel().uiState.value

        assertNotNull(etat.erreur)
        assertFalse(etat.chargement)
    }

    // ---------- Matière et fiches ----------

    @Test
    fun selectionDUneMatiere_laMemorise() {
        repository.matieres = listOf(maths)
        val vm = creerViewModel()

        vm.selectionnerMatiere(maths)
        attendre()

        val etat = vm.uiState.value
        assertEquals(maths, etat.matiereSelectionnee)
        assertFalse(etat.chargementFiches)
    }

    @Test
    fun matiereSansFiche_afficheLeMessageVide() {
        repository.matieres = listOf(maths)
        repository.fiches = emptyList()
        val vm = creerViewModel()

        vm.selectionnerMatiere(maths)
        attendre()

        val etat = vm.uiState.value
        assertTrue(etat.aucuneFiche)
        assertEquals(MSG_AUCUNE_FICHE, etat.messageFichesVides)
    }

    // ---------- Nombre de questions ----------

    @Test
    fun nombreDeQuestionsValide_estMemorise() {
        val vm = creerViewModel()

        vm.selectionnerNombreQuestions(5)

        assertEquals(5, vm.uiState.value.nombreQuestions)
        assertNull(vm.uiState.value.messageValidation)
    }

    @Test
    fun nombreDeQuestionsInvalide_estRefuse() {
        val vm = creerViewModel()

        vm.selectionnerNombreQuestions(7)

        val etat = vm.uiState.value
        assertEquals(10, etat.nombreQuestions) // valeur par défaut inchangée
        assertNotNull(etat.messageValidation)
    }

    // ---------- Lancement du quiz ----------

    @Test
    fun lancerSansMatiere_donneUnMessage() {
        val vm = creerViewModel()

        vm.preparerQuiz()
        attendre()

        val etat = vm.uiState.value
        assertNotNull(etat.messageValidation)
        assertNull(etat.parametresQuiz)
    }

    @Test
    fun lancerSansQuestionDisponible_donneUnMessage() {
        repository.matieres = listOf(maths)
        repository.questions = emptyList()
        val vm = creerViewModel()
        vm.selectionnerMatiere(maths)
        attendre()

        vm.preparerQuiz()
        attendre()

        val etat = vm.uiState.value
        assertNotNull(etat.messageValidation)
        assertNull(etat.parametresQuiz)
    }

    @Test
    fun lancerAvecPeuDeQuestions_donneUnMessage() {
        repository.matieres = listOf(maths)
        repository.questions = (1L..3L).map { uneQuestion(it) }
        val vm = creerViewModel()
        vm.selectionnerMatiere(maths)
        attendre()

        vm.preparerQuiz() // 10 questions demandées, 3 disponibles
        attendre()

        val etat = vm.uiState.value
        assertTrue(etat.messageValidation!!.contains("3"))
        assertNull(etat.parametresQuiz)
    }

    @Test
    fun lancerAvecAssezDeQuestions_prepareLesParametres() {
        repository.matieres = listOf(maths)
        repository.questions = (1L..10L).map { uneQuestion(it) }
        val vm = creerViewModel()
        vm.selectionnerMatiere(maths)
        attendre()

        vm.preparerQuiz()
        attendre()

        val etat = vm.uiState.value
        assertEquals(ParametresQuiz("BAC", 1L, 10), etat.parametresQuiz)
        assertNull(etat.messageValidation)
    }

    @Test
    fun consommerLancement_viderLesParametres() {
        repository.matieres = listOf(maths)
        repository.questions = (1L..10L).map { uneQuestion(it) }
        val vm = creerViewModel()
        vm.selectionnerMatiere(maths)
        attendre()
        vm.preparerQuiz()
        attendre()

        vm.consommerLancement()

        assertNull(vm.uiState.value.parametresQuiz)
    }
}