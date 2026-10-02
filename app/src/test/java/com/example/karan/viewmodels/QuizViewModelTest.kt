package com.example.karan.viewmodels

import com.example.karan.data.repository.RevisionRepository
import com.example.karan.models.Fiche
import com.example.karan.models.Matiere
import com.example.karan.models.Question
import com.example.karan.models.SessionQuiz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/** Faux repository : pas de vraie base Room dans les tests. */
private class FakeRevisionRepository(
    var questions: List<Question> = emptyList(),
    var erreurChargement: Boolean = false
) : RevisionRepository {

    val sessionsEnregistrees = mutableListOf<SessionQuiz>()

    override suspend fun getQuestions(
        examen: String,
        matiereId: Long,
        nombreQuestions: Int
    ): List<Question> {
        if (erreurChargement) throw RuntimeException("Erreur de chargement")
        return questions
    }

    override suspend fun saveSession(session: SessionQuiz) {
        sessionsEnregistrees.add(session)
    }

    override suspend fun getDerniereSession(): SessionQuiz? = sessionsEnregistrees.lastOrNull()

    override suspend fun getMatiere(matiereId: Long): Matiere? = null

    override suspend fun getSessionsByMatiere(matiereId: Long): List<SessionQuiz> =
        sessionsEnregistrees.filter { it.matiereId == matiereId }

    override suspend fun getMatieres(examen: String): List<Matiere> = emptyList()

    override suspend fun getFiches(matiereId: Long): List<Fiche> = emptyList()

    override suspend fun countQuestions(examen: String, matiereId: Long): Int = questions.size

    override suspend fun getDernieresSessions(limite: Int): List<SessionQuiz> =
        sessionsEnregistrees.takeLast(limite)

    override suspend fun countErreursARevoir(examen: String): Int = 0
}

@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val repository = FakeRevisionRepository()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ---------- Outils ----------

    private fun question(id: Long, reponseCorrecte: String = "A") = Question(
        id = id,
        matiereId = 1L,
        examen = "BAC",
        enonce = "Question $id",
        propositionA = "Proposition A",
        propositionB = "Proposition B",
        propositionC = "Proposition C",
        propositionD = "Proposition D",
        reponseCorrecte = reponseCorrecte,
        explication = "Explication $id"
    )

    private fun creerViewModel(
        nombreQuestions: Int = 3,
        dureeSecondes: Int = 90
    ): QuizViewModel {
        val vm = QuizViewModel(repository, "BAC", 1L, nombreQuestions, dureeSecondes)
        dispatcher.scheduler.runCurrent() // termine le chargement, sans faire avancer le chrono
        return vm
    }

    private fun QuizViewModel.ready() = uiState.value as QuizUiState.Ready

    private fun trois() = listOf(question(1), question(2), question(3))

    // ---------- Chargement ----------

    @Test
    fun chargement_affichePremiereQuestion() {
        repository.questions = trois()
        val vm = creerViewModel()

        val etat = vm.ready()
        assertEquals(3, etat.totalQuestions)
        assertEquals(0, etat.indexActuel)
        assertEquals(0, etat.score)
        assertEquals(1L, etat.question.id)
    }

    @Test
    fun chargement_aucuneQuestion_donneEtatEmpty() {
        repository.questions = emptyList()
        val vm = creerViewModel()

        assertTrue(vm.uiState.value is QuizUiState.Empty)
    }

    @Test
    fun chargement_moinsDeQuestionsQueDemande() {
        repository.questions = listOf(question(1), question(2))
        val vm = creerViewModel(nombreQuestions = 5)

        assertEquals(2, vm.ready().totalQuestions)
    }

    @Test
    fun chargement_erreur_donneEtatError() {
        repository.erreurChargement = true
        val vm = creerViewModel()

        assertTrue(vm.uiState.value is QuizUiState.Error)
    }

    // ---------- Réponses et score ----------

    @Test
    fun bonneReponse_augmenteLeScore() {
        repository.questions = trois()
        val vm = creerViewModel()

        vm.selectionnerReponse("A")

        val etat = vm.ready()
        assertEquals(1, etat.score)
        assertEquals(true, etat.estCorrecte)
        assertTrue(etat.correctionAffichee)
    }

    @Test
    fun mauvaiseReponse_neChangePasLeScore() {
        repository.questions = trois()
        val vm = creerViewModel()

        vm.selectionnerReponse("B")

        val etat = vm.ready()
        assertEquals(0, etat.score)
        assertEquals(false, etat.estCorrecte)
        assertTrue(etat.correctionAffichee)
    }

    @Test
    fun doubleReponse_estIgnoree() {
        repository.questions = trois()
        val vm = creerViewModel()

        vm.selectionnerReponse("B")
        vm.selectionnerReponse("A")

        val etat = vm.ready()
        assertEquals("B", etat.reponseSelectionnee)
        assertEquals(0, etat.score)
    }

    @Test
    fun reponseVide_estIgnoree() {
        repository.questions = trois()
        val vm = creerViewModel()

        vm.selectionnerReponse("")

        val etat = vm.ready()
        assertEquals(null, etat.reponseSelectionnee)
        assertFalse(etat.correctionAffichee)
    }

    // ---------- Progression ----------

    @Test
    fun questionSuivante_sansReponse_resteSurLaMemeQuestion() {
        repository.questions = trois()
        val vm = creerViewModel()

        vm.questionSuivante()

        assertEquals(0, vm.ready().indexActuel)
    }

    @Test
    fun questionSuivante_apresReponse_avance() {
        repository.questions = trois()
        val vm = creerViewModel()

        vm.selectionnerReponse("A")
        vm.questionSuivante()

        val etat = vm.ready()
        assertEquals(1, etat.indexActuel)
        assertEquals(null, etat.reponseSelectionnee)
    }

    // ---------- Fin du quiz ----------

    @Test
    fun finDuQuiz_calculeScoreErreursEtEnregistreLaSession() {
        repository.questions = trois()
        val vm = creerViewModel()

        vm.selectionnerReponse("A"); vm.questionSuivante() // bonne
        vm.selectionnerReponse("B"); vm.questionSuivante() // mauvaise
        vm.selectionnerReponse("A"); vm.questionSuivante() // bonne (dernière)
        dispatcher.scheduler.runCurrent()                  // laisse la sauvegarde se faire

        val fin = vm.uiState.value as QuizUiState.Finished
        assertEquals(2, fin.score)
        assertEquals(3, fin.totalQuestions)
        assertEquals(66, fin.pourcentage)
        assertFalse(fin.tempsEcoule)
        assertEquals(1, fin.questionsErronees.size)
        assertEquals(2L, fin.questionsErronees.first().id)
        assertTrue(fin.sessionEnregistree)

        assertEquals(1, repository.sessionsEnregistrees.size)
        val session = repository.sessionsEnregistrees.first()
        assertEquals(2, session.score)
        assertEquals(3, session.total)
    }

    // ---------- Chronomètre ----------

    @Test
    fun chrono_diminueChaqueSeconde() {
        repository.questions = trois()
        val vm = creerViewModel(dureeSecondes = 90)
        assertEquals(90, vm.ready().tempsRestantSecondes)

        dispatcher.scheduler.advanceTimeBy(3_000)
        dispatcher.scheduler.runCurrent()

        assertEquals(87, vm.ready().tempsRestantSecondes)
    }

    @Test
    fun tempsEcoule_terminelQuiz() {
        repository.questions = trois()
        val vm = creerViewModel(dureeSecondes = 5)

        dispatcher.scheduler.advanceTimeBy(5_000)
        dispatcher.scheduler.runCurrent()

        val fin = vm.uiState.value as QuizUiState.Finished
        assertTrue(fin.tempsEcoule)
        assertEquals(5, fin.dureeSecondes)
        assertNotNull(fin)
    }
}