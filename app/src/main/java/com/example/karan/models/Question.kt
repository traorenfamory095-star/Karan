package com.example.karan.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "questions",
    foreignKeys = [
        ForeignKey(
            entity = Matiere::class,
            parentColumns = ["id"],
            childColumns = ["matiereId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["matiereId"])
    ]
)
data class Question(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val matiereId: Long,

    /**
     * BAC ou BEPC
     */
    val examen: String,

    /**
     * Chapitre auquel appartient la question.
     */
    val chapitre: String = "",

    val enonce: String,

    val optionA: String,

    val optionB: String,

    val optionC: String,

    val optionD: String,

    /**
     * Valeur attendue : "A", "B", "C" ou "D"
     */
    val reponseCorrecte: String,

    /**
     * Explication affichée après validation.
     */
    val explication: String = ""
)