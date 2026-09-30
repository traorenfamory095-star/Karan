package com.example.karan.data.repository

import com.example.karan.models.Question
import com.example.karan.models.SessionQuiz

/**
 * Interface définissant les opérations d'accès aux données de révision.
 */
interface RevisionRepository {
    suspend fun getQuestions(examen: String, matiereId: Long, nombreQuestions: Int): List<Question>
    suspend fun saveSession(session: SessionQuiz)
}
