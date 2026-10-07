package com.example.karan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.karan.models.Question

@Dao
interface QuestionDao {

    @Query("""
        SELECT * FROM questions
        WHERE examen = :examen
        AND matiereId = :matiereId
        ORDER BY RANDOM()
        LIMIT :nombreQuestions
    """)
    suspend fun getQuestions(
        examen: String,
        matiereId: Long,
        nombreQuestions: Int
    ): List<Question>

    @Query("""
        SELECT COUNT(*) FROM questions
        WHERE examen = :examen
        AND matiereId = :matiereId
    """)
    suspend fun countQuestions(
        examen: String,
        matiereId: Long
    ): Int

    @Query("""
        SELECT * FROM questions
        WHERE id = :questionId
        LIMIT 1
    """)
    suspend fun getQuestion(questionId: Long): Question?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>)

    @Query("DELETE FROM questions")
    suspend fun deleteAll()
}