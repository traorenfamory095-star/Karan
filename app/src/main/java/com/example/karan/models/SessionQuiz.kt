package com.example.karan.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sessions_quiz",
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
data class SessionQuiz(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val matiereId: Long,

    /**
     * Date de la session.
     * Stockée sous forme de timestamp.
     */
    val date: Long,

    val score: Int,

    val total: Int,

    /**
     * Durée réelle du quiz en secondes.
     */
    val dureeSecondes: Int
)