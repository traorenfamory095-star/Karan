package com.example.karan.data.repository

import com.example.karan.models.ErreurQuestion
import com.example.karan.models.Fiche
import com.example.karan.models.Matiere
import com.example.karan.models.Question
import com.example.karan.models.SessionQuiz

interface RevisionRepository {

    // MATIERES

    suspend fun getMatieres(
        examen: String
    ): List<Matiere>

    suspend fun getMatiere(
        matiereId: Long
    ): Matiere?


    // QUESTIONS

    suspend fun getQuestions(
        examen: String,
        matiereId: Long,
        nombreQuestions: Int
    ): List<Question>

    suspend fun countQuestions(
        examen: String,
        matiereId: Long
    ): Int


    // FICHES

    suspend fun getFiches(
        matiereId: Long
    ): List<Fiche>

    suspend fun countFiches(
        matiereId: Long
    ): Int


    // SESSIONS

    suspend fun saveSession(
        session: SessionQuiz
    ): Long

    suspend fun getDernieresSessions(
        limite: Int
    ): List<SessionQuiz>

    suspend fun getDerniereSession(): SessionQuiz?

    suspend fun getSessionsByMatiere(
        matiereId: Long
    ): List<SessionQuiz>


    // ERREURS

    suspend fun countErreursARevoir(
        examen: String
    ): Int


    suspend fun saveErreur(erreur: ErreurQuestion)

    suspend fun getErreurs(examen: String): List<ErreurQuestion>

    suspend fun supprimerErreur(questionId: Long)
}