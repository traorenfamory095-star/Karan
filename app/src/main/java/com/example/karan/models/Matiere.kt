package com.example.karan.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matieres")
data class Matiere(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val nom: String,

    /**
     * Valeurs attendues :
     * "BAC" ou "BEPC"
     */
    val examen: String,

    val nombreChapitres: Int = 0
)