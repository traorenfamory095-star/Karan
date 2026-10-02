package com.example.karan.viewmodels

import com.example.karan.data.repository.RevisionRepository
import com.example.karan.models.Fiche
import com.example.karan.models.Matiere
import com.example.karan.models.Question
import com.example.karan.models.SessionQuiz

/**
 * Faux repository partagé par les tests de Resultat, Matieres et Accueil.
 * Pas de vraie base Room : on remplit simplement les listes avant chaque test.
 */
class FakeRepository : RevisionRepository {

    var questions: List<Question> = emptyList()
    var matieres: List<Matiere> = emptyList()
    var fiches: List<Fiche> = emptyList()
    val sessions = mutableListOf<SessionQuiz>()
    var nombreErreursARevoir = 0

    /** Mettre à true pour simuler une erreur de lecture. */
    var erreur = false

    private fun verifier() {
        if (erreur) throw RuntimeException("Erreur de test")
    }

    override suspend fun getQuestions(
        examen: String,
        matiereId: Long,
        nombreQuestions: Int
    ): List<Question> {
        verifier()
        return questions
    }

    override suspend fun saveSession(session: SessionQuiz) {
        sessions.add(session)
    }

    override suspend fun getDerniereSession(): SessionQuiz? {
        verifier()
        return sessions.maxByOrNull { it.date }
    }

    override suspend fun getMatiere(matiereId: Long): Matiere? {
        verifier()
        return matieres.find { it.id == matiereId }
    }

    override suspend fun getSessionsByMatiere(matiereId: Long): List<SessionQuiz> {
        verifier()
        return sessions.filter { it.matiereId == matiereId }
    }

    override suspend fun getMatieres(examen: String): List<Matiere> {
        verifier()
        return matieres.filter { it.examen == examen }
    }

    override suspend fun getFiches(matiereId: Long): List<Fiche> {
        verifier()
        return fiches
    }

    override suspend fun countQuestions(examen: String, matiereId: Long): Int {
        verifier()
        return questions.count { it.matiereId == matiereId && it.examen == examen }
    }

    override suspend fun getDernieresSessions(limite: Int): List<SessionQuiz> {
        verifier()
        return sessions.sortedByDescending { it.date }.take(limite)
    }

    override suspend fun countErreursARevoir(examen: String): Int {
        verifier()
        return nombreErreursARevoir
    }
}

// ---------- Outils pour fabriquer des données de test ----------

fun uneMatiere(id: Long, nom: String, examen: String = "BAC") =
    Matiere(id = id, nom = nom, examen = examen)

fun uneQuestion(id: Long, matiereId: Long = 1L, examen: String = "BAC") = Question(
    id = id,
    matiereId = matiereId,
    examen = examen,
    enonce = "Question $id",
    propositionA = "Proposition A",
    propositionB = "Proposition B",
    propositionC = "Proposition C",
    propositionD = "Proposition D",
    reponseCorrecte = "A",
    explication = "Explication $id"
)

fun uneSession(
    matiereId: Long,
    score: Int,
    total: Int = 10,
    date: Long = 1L,
    dureeSecondes: Int = 60
) = SessionQuiz(
    matiereId = matiereId,
    date = date,
    score = score,
    total = total,
    dureeSecondes = dureeSecondes
)