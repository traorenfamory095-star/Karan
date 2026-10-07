package com.example.karan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.karan.models.SessionQuiz

@Dao
interface SessionQuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(
        session: SessionQuiz
    ): Long

    @Query("""
        SELECT * FROM sessions_quiz
        ORDER BY date DESC
        LIMIT :limite
    """)
    suspend fun getDernieresSessions(
        limite: Int
    ): List<SessionQuiz>

    @Query("""
        SELECT * FROM sessions_quiz
        ORDER BY date DESC
        LIMIT 1
    """)
    suspend fun getDerniereSession(): SessionQuiz?

    @Query("""
        SELECT * FROM sessions_quiz
        WHERE matiereId = :matiereId
        ORDER BY date DESC
    """)
    suspend fun getSessionsByMatiere(
        matiereId: Long
    ): List<SessionQuiz>

    @Query("""
        SELECT COUNT(*) FROM sessions_quiz
        WHERE matiereId = :matiereId
    """)
    suspend fun countSessionsByMatiere(
        matiereId: Long
    ): Int

    @Query("DELETE FROM sessions_quiz")
    suspend fun deleteAll()
}