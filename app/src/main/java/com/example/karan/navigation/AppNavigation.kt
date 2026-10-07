package com.example.karan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.karan.data.repository.RevisionRepository
import com.example.karan.viewmodels.AccueilViewModel
import com.example.karan.viewmodels.MatieresViewModel
import com.example.karan.viewmodels.QuizUiState
import com.example.karan.viewmodels.QuizViewModel
import com.example.karan.viewmodels.ResultatViewModel
import com.example.karan.viewmodels.toCorrectionItems
import com.example.karan.views.AccueilScreen
import com.example.karan.views.MatieresScreen
import com.example.karan.views.QuizScreen
import com.example.karan.views.ResultatScreen

/*
 * ============================================================
 * APP NAVIGATION
 * ============================================================
 *
 * Responsable : N'famory Traore
 *
 * Parcours principal :
 *
 * Accueil
 *    ↓
 * Matières
 *    ↓
 * Quiz
 *    ↓
 * Résultat
 *
 * La navigation coordonne uniquement :
 *
 * - les destinations ;
 * - les paramètres ;
 * - les ViewModels ;
 * - les callbacks.
 *
 * ============================================================
 */

private object Routes {

    const val ACCUEIL = "accueil"

    const val MATIERES = "matieres"

    const val QUIZ = "quiz"

    const val RESULTAT = "resultat"

    const val EXAMEN = "examen"

    const val MATIERE_ID = "matiereId"

    const val NOMBRE_QUESTIONS = "nombreQuestions"

    const val QUIZ_ROUTE =
        "$QUIZ/{$EXAMEN}/{$MATIERE_ID}/{$NOMBRE_QUESTIONS}"
}

@Composable
fun AppNavigation(
    repository: RevisionRepository
) {
    val navController = rememberNavController()

    /*
     * Résultat temporaire du dernier quiz.
     *
     * Il permet de transmettre le résultat du QuizScreen
     * vers ResultatScreen.
     */
    var dernierResultat by remember {
        mutableStateOf<QuizUiState.Finished?>(null)
    }

    NavHost(
        navController = navController,
        startDestination = Routes.ACCUEIL
    ) {

        /* ====================================================
         * ACCUEIL
         * ==================================================== */

        composable(
            route = Routes.ACCUEIL
        ) {

            val viewModel: AccueilViewModel = viewModel(
                factory = AccueilViewModel.Factory(
                    repository = repository
                )
            )

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
                viewModel.charger()
            }

            AccueilScreen(
                uiState = uiState,

                onExamenSelected = { examen ->
                    viewModel.selectionnerExamen(examen)

                    navController.navigate(
                        "${Routes.MATIERES}?${Routes.EXAMEN}=$examen"
                    )
                },

                onOpenMatieres = {
                    navController.navigate(
                        Routes.MATIERES
                    )
                },

                onReprendreRevision = {
                    navController.navigate(
                        Routes.MATIERES
                    )
                },

                onRevoirErreurs = {
                    navController.navigate(
                        Routes.MATIERES
                    )
                }
            )
        }

        /* ====================================================
         * MATIÈRES
         * ==================================================== */

        composable(
            route = "${Routes.MATIERES}?${Routes.EXAMEN}={${Routes.EXAMEN}}",
            arguments = listOf(
                navArgument(Routes.EXAMEN) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->

            val examen =
                backStackEntry.arguments
                    ?.getString(Routes.EXAMEN)
                    ?: "BAC"

            val viewModel: MatieresViewModel = viewModel(
                factory = MatieresViewModel.Factory(
                    repository = repository,
                    examenInitial = examen
                )
            )

            MatieresScreen(
                viewModel = viewModel,

                onRetourAccueil = {
                    navController.popBackStack()
                },

                onLancerQuiz = { parametres ->

                    navController.navigate(
                        "${Routes.QUIZ}/" +
                                "${parametres.examen}/" +
                                "${parametres.matiereId}/" +
                                parametres.nombreQuestions
                    )
                }
            )
        }

        /* ====================================================
         * QUIZ
         * ==================================================== */

        composable(
            route = Routes.QUIZ_ROUTE,
            arguments = listOf(
                navArgument(Routes.EXAMEN) {
                    type = NavType.StringType
                },
                navArgument(Routes.MATIERE_ID) {
                    type = NavType.LongType
                },
                navArgument(Routes.NOMBRE_QUESTIONS) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val examen =
                backStackEntry.arguments
                    ?.getString(Routes.EXAMEN)
                    ?: "BAC"

            val matiereId =
                backStackEntry.arguments
                    ?.getLong(Routes.MATIERE_ID)
                    ?: 0L

            val nombreQuestions =
                backStackEntry.arguments
                    ?.getInt(Routes.NOMBRE_QUESTIONS)
                    ?: 10

            val viewModel: QuizViewModel = viewModel(
                factory = QuizViewModel.Factory(
                    repository = repository,
                    examen = examen,
                    matiereId = matiereId,
                    nombreQuestions = nombreQuestions
                )
            )

            QuizScreen(
                viewModel = viewModel,

                onRetourMatieres = {
                    navController.popBackStack()
                },

                onAfficherResultat = { resultat ->

                    dernierResultat = resultat

                    navController.navigate(
                        Routes.RESULTAT
                    )
                }
            )
        }

        /* ====================================================
         * RÉSULTAT
         * ==================================================== */

        composable(
            route = Routes.RESULTAT
        ) {

            val resultat = dernierResultat

            if (resultat == null) {

                navController.navigate(
                    Routes.ACCUEIL
                ) {
                    popUpTo(Routes.ACCUEIL) {
                        inclusive = true
                    }
                }

            } else {

                /*
                 * Le ResultatViewModel reçoit directement
                 * la correction produite par le QuizViewModel.
                 *
                 * La conversion est faite avec l'extension
                 * toCorrectionItems().
                 */
                val correction = resultat.toCorrectionItems()

                val viewModel: ResultatViewModel = viewModel(
                    factory = ResultatViewModel.Factory(
                        repository = repository,
                        correction = correction
                    )
                )

                ResultatScreen(
                    uiState = viewModel.uiState.collectAsStateWithLifecycle().value,

                    onNextQuestion = {
                        navController.popBackStack()
                    },

                    onRetourAccueil = {
                        navController.popBackStack(
                            Routes.ACCUEIL,
                            inclusive = false
                        )
                    },

                    onVoirPlusProgression = {
                        // Progression intégrée au MVP.
                    }
                )
            }
        }
    }
}