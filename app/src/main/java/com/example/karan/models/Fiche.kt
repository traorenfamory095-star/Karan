package com.example.karan.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Représente une fiche de révision disponible hors connexion.
 */
@Entity(tableName = "fiches")
data class Fiche(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val matiereId: Long,
    val examen: String = "",
    val titre: String,
    val chapitre: String = "",
    val contenu: String,
    val ordre: Int = 0
)
