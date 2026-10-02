package com.example.karan.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Représente une matière (ex: Mathématiques, Français...).
 */
@Entity(tableName = "matieres")
data class Matiere(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nom: String,
    val examen: String = "",
    val icone: String = ""
)
