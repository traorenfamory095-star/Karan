package com.example.karan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.karan.models.Matiere

@Dao
interface MatiereDao {

    @Query("""
        SELECT * FROM matieres
        WHERE examen = :examen
        ORDER BY nom ASC
    """)
    suspend fun getMatieres(
        examen: String
    ): List<Matiere>

    @Query("""
        SELECT * FROM matieres
        WHERE id = :matiereId
        LIMIT 1
    """)
    suspend fun getMatiere(
        matiereId: Long
    ): Matiere?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatiere(
        matiere: Matiere
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatieres(
        matieres: List<Matiere>
    )

    @Query("DELETE FROM matieres")
    suspend fun deleteAll()
}