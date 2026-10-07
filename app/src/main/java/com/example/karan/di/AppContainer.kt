package com.example.karan.di

import android.content.Context
import com.example.karan.data.local.AppDatabase
import com.example.karan.data.local.SeedData
import com.example.karan.data.repository.RevisionRepository
import com.example.karan.data.repository.RevisionRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
 * ============================================================
 * APP CONTAINER
 * ============================================================
 *
 * Responsable : N'famory Traore
 *
 * Rôle :
 * Centraliser la création des dépendances principales
 * de l'application.
 *
 * Architecture :
 *
 * AppContainer
 *      ↓
 * AppDatabase
 *      ↓
 * DAO
 *      ↓
 * RevisionRepositoryImpl
 *      ↓
 * RevisionRepository
 *      ↓
 * ViewModel
 *
 * ============================================================
 */

class AppContainer(
    context: Context
) {

    /*
     * ========================================================
     * BASE DE DONNÉES
     * ========================================================
     */

    private val database: AppDatabase =
        AppDatabase.getDatabase(context)

    /*
     * ========================================================
     * DAO
     * ========================================================
     */

    private val matiereDao =
        database.matiereDao()

    private val questionDao =
        database.questionDao()

    private val sessionQuizDao =
        database.sessionQuizDao()

    private val ficheDao =
        database.ficheDao()

    private val erreurQuestionDao = database.erreurQuestionDao()

    /*
     * ========================================================
     * REPOSITORY
     * ========================================================
     */

    val repository: RevisionRepository =
        RevisionRepositoryImpl(
            matiereDao = matiereDao,
            questionDao = questionDao,
            sessionQuizDao = sessionQuizDao,
            ficheDao = ficheDao,
            erreurQuestionDao = erreurQuestionDao
        )

    /*
     * ========================================================
     * DONNÉES INITIALES
     * ========================================================
     *
     * On initialise les données uniquement si la base
     * ne contient encore aucune matière.
     */

    init {
        initialiserDonnees()
    }

    private fun initialiserDonnees() {

        CoroutineScope(Dispatchers.IO).launch {

            val matieresExistantes =
                matiereDao.getMatieres("BAC") +
                        matiereDao.getMatieres("BEPC")

            if (matieresExistantes.isNotEmpty()) {
                return@launch
            }

            matiereDao.insertMatieres(
                SeedData.matieres
            )

            questionDao.insertQuestions(
                SeedData.questions
            )

            ficheDao.insertFiches(
                SeedData.fiches
            )
        }
    }
}

