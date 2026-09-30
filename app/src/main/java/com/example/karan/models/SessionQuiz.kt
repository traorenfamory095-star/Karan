package com.example.karan.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Représente une session de quiz terminée.
 */
@Entity(tableName = "sessions_quiz")
data class SessionQuiz(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val matiereId: Long,
    val examen: String = "",
    val score: Int,
    val total: Int,
    val dureeSecondes: Int,
    val date: Long
)
