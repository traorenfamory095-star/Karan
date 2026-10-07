package com.example.karan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.karan.models.Fiche

@Dao
interface FicheDao {

    @Query("""
        SELECT * FROM fiches
        WHERE matiereId = :matiereId
        ORDER BY titre ASC
    """)
    suspend fun getFiches(
        matiereId: Long
    ): List<Fiche>

    @Query("""
        SELECT * FROM fiches
        WHERE id = :ficheId
        LIMIT 1
    """)
    suspend fun getFiche(
        ficheId: Long
    ): Fiche?

    @Query("""
        SELECT COUNT(*) FROM fiches
        WHERE matiereId = :matiereId
    """)
    suspend fun countFiches(
        matiereId: Long
    ): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiche(
        fiche: Fiche
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiches(
        fiches: List<Fiche>
    )

    @Query("DELETE FROM fiches")
    suspend fun deleteAll()
}