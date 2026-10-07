package com.example.karan.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "fiches",
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
data class Fiche(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val matiereId: Long,

    /**
     * Nom du chapitre ou de la fiche.
     */
    val titre: String,

    /**
     * Contenu de la fiche.
     */
    val contenu: String,

    val chapitre: String = ""
)