package com.example.karan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.karan.models.ErreurQuestion

@Dao
interface ErreurQuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertErreur(erreur: ErreurQuestion)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertErreurs(erreurs: List<ErreurQuestion>)

    @Query("""
        SELECT * FROM erreurs_questions
        WHERE examen = :examen
        ORDER BY date DESC
    """)
    suspend fun getErreurs(examen: String): List<ErreurQuestion>

    @Query("""
        SELECT COUNT(*) FROM erreurs_questions
        WHERE examen = :examen
    """)
    suspend fun countErreurs(examen: String): Int

    @Query("""
        DELETE FROM erreurs_questions
        WHERE questionId = :questionId
    """)
    suspend fun supprimerErreur(questionId: Long)

    @Query("DELETE FROM erreurs_questions")
    suspend fun deleteAll()
}