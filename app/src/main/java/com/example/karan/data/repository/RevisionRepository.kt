package com.example.karan.data.repository

import com.example.karan.models.Fiche
import com.example.karan.models.Matiere
import com.example.karan.models.Question
import com.example.karan.models.SessionQuiz

/**
 * Interface définissant les opérations d'accès aux données de révision.
 */
interface RevisionRepository {
    suspend fun getQuestions(examen: String, matiereId: Long, nombreQuestions: Int): List<Question>
    suspend fun saveSession(session: SessionQuiz)
    suspend fun getDerniereSession(): SessionQuiz?
    suspend fun getMatiere(matiereId: Long): Matiere?
    suspend fun getSessionsByMatiere(matiereId: Long): List<SessionQuiz>
    suspend fun getMatieres(examen: String): List<Matiere>
    suspend fun getFiches(matiereId: Long): List<Fiche>
    suspend fun countQuestions(examen: String, matiereId: Long): Int
    suspend fun getDernieresSessions(limite: Int): List<SessionQuiz>
    suspend fun countErreursARevoir(examen: String): Int
}
