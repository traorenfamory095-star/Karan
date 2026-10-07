package com.example.karan.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index
@Entity(
    tableName = "erreurs_questions",
    indices = [Index(value = ["questionId"], unique = true)]
)
data class ErreurQuestion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val questionId: Long,
    val matiereId: Long,
    val examen: String,
    val date: Long
)