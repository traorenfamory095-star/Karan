package com.example.karan.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Représente une question de quiz.
 */
@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val matiereId: Long,
    val examen: String,
    val enonce: String,
    val propositionA: String,
    val propositionB: String,
    val propositionC: String,
    val propositionD: String,
    val reponseCorrecte: String,
    val explication: String
)
