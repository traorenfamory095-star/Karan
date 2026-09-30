package com.example.karan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.karan.ui.theme.KaranTheme
import com.example.karan.views.AccueilScreen
import com.example.karan.views.AccueilUiState
import com.example.karan.views.ExamenType
import com.example.karan.views.MatiereUiItem
import com.example.karan.views.MatieresScreen
import com.example.karan.views.MatieresUiState
import com.example.karan.views.QuestionUiItem
import com.example.karan.views.QuizScreen
import com.example.karan.views.QuizUiState
import com.example.karan.views.ResultatScreen
import com.example.karan.views.ResultatUiState
import com.example.karan.views.SubjectProgressItem
import com.example.karan.views.SubjectProgressUiItem

enum class ScreenDestination {
    ACCUEIL,
    MATIERES,
    QUIZ,
    RESULTAT,
    PROGRESSION
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KaranTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    KaranAppNavHost()
                }
            }
        }
    }
}

@Composable
fun KaranAppNavHost() {
    var currentScreen by remember { mutableStateOf(ScreenDestination.ACCUEIL) }
    var selectedExamen by remember { mutableStateOf(ExamenType.BAC) }
    var selectedMatiereNom by remember { mutableStateOf("Mathématiques") }
    var selectedQuestionCount by remember { mutableIntStateOf(10) }

    val sampleProgressList = remember {
        listOf(
            SubjectProgressItem("1", "Mathématiques", 0.78f, "78%", 0xFF0066FF, "📐"),
            SubjectProgressItem("2", "Français", 0.65f, "65%", 0xFFEF4444, "📕"),
            SubjectProgressItem("3", "Physique-Chimie", 0.72f, "72%", 0xFF8B5CF6, "🧪"),
            SubjectProgressItem("4", "SVT", 0.58f, "58%", 0xFF10B981, "🌿")
        )
    }

    val sampleMatieres = remember(selectedExamen) {
        if (selectedExamen == ExamenType.BAC) {
            listOf(
                MatiereUiItem("1", "Mathématiques", 12, 0xFF0066FF, "📐"),
                MatiereUiItem("2", "Français", 10, 0xFFEF4444, "📕"),
                MatiereUiItem("3", "Physique-Chimie", 8, 0xFF8B5CF6, "🧪"),
                MatiereUiItem("4", "SVT", 7, 0xFF10B981, "🌿"),
                MatiereUiItem("5", "Histoire-Géographie", 6, 0xFFF59E0B, "🌐"),
                MatiereUiItem("6", "Philosophie", 5, 0xFF06B6D4, "🧠")
            )
        } else {
            listOf(
                MatiereUiItem("1", "Mathématiques BEPC", 10, 0xFF0066FF, "📐"),
                MatiereUiItem("2", "Dictée-Questions", 8, 0xFFEF4444, "📕"),
                MatiereUiItem("3", "Sciences Physiques", 7, 0xFF8B5CF6, "🧪"),
                MatiereUiItem("4", "SVT BEPC", 6, 0xFF10B981, "🌿"),
                MatiereUiItem("5", "Histoire-Géo BEPC", 5, 0xFFF59E0B, "🌐")
            )
        }
    }

    var selectedAnswer by remember { mutableStateOf<String?>("B") }
    var isAnswerSubmitted by remember { mutableStateOf(true) }

    val sampleQuestion = remember(selectedMatiereNom) {
        QuestionUiItem(
            id = "q1",
            enonce = "Quel est le résultat de f(5) = 2 x 5 + 3 ?",
            optionA = "7",
            optionB = "13",
            optionC = "10",
            optionD = "8",
            bonneReponse = "B",
            explication = "On a f(5) = 2 x 5 + 3\nf(5) = 10 + 3\nf(5) = 13",
            matiereNom = selectedMatiereNom,
            chapitreNom = "Fonctions"
        )
    }

    val bottomBarComposable: @Composable () -> Unit = {
        KaranBottomBar(
            currentDestination = currentScreen,
            onDestinationSelected = { dest ->
                currentScreen = dest
            }
        )
    }

    when (currentScreen) {
        ScreenDestination.ACCUEIL -> {
            AccueilScreen(
                uiState = AccueilUiState(
                    studentName = "N'famory",
                    selectedExamen = selectedExamen,
                    matieres = sampleMatieres,
                    subjectProgressList = sampleProgressList,
                    lastRevisionTitle = "Mathématiques - Chapitre 3",
                    countErrorsToReview = 5,
                    errorsSubjectsText = "Mathématiques, Physique-Chimie..."
                ),
                onExamenSelected = { examen ->
                    selectedExamen = examen
                },
                onOpenMatieres = {
                    currentScreen = ScreenDestination.MATIERES
                },
                onMatiereClick = { matiere ->
                    selectedMatiereNom = matiere.nom
                    selectedQuestionCount = 10
                    selectedAnswer = null
                    isAnswerSubmitted = false
                    currentScreen = ScreenDestination.QUIZ
                },
                onReprendreRevision = {
                    selectedMatiereNom = "Mathématiques"
                    currentScreen = ScreenDestination.QUIZ
                },
                onRevoirErreurs = {
                    selectedMatiereNom = "Révision Erreurs"
                    currentScreen = ScreenDestination.QUIZ
                },
                bottomBar = bottomBarComposable
            )
        }

        ScreenDestination.MATIERES -> {
            MatieresScreen(
                uiState = MatieresUiState(
                    selectedExamen = selectedExamen,
                    matieres = sampleMatieres,
                    isLoading = false
                ),
                onExamenSelected = { examen ->
                    selectedExamen = examen
                },
                onStartQuiz = { matiereId, nombreQuestions ->
                    val matiereObj = sampleMatieres.find { it.id == matiereId }
                    selectedMatiereNom = matiereObj?.nom ?: "Matière"
                    selectedQuestionCount = nombreQuestions
                    selectedAnswer = null
                    isAnswerSubmitted = false
                    currentScreen = ScreenDestination.QUIZ
                },
                onOpenFiches = { matiereId ->
                    val matiereObj = sampleMatieres.find { it.id == matiereId }
                    selectedMatiereNom = matiereObj?.nom ?: "Matière"
                    selectedQuestionCount = 10
                    selectedAnswer = null
                    isAnswerSubmitted = false
                    currentScreen = ScreenDestination.QUIZ
                },
                onBackClick = {
                    currentScreen = ScreenDestination.ACCUEIL
                },
                bottomBar = bottomBarComposable
            )
        }

        ScreenDestination.QUIZ -> {
            QuizScreen(
                uiState = QuizUiState(
                    currentQuestionIndex = 2,
                    totalQuestions = selectedQuestionCount,
                    timeRemainingSeconds = 42,
                    currentQuestion = sampleQuestion,
                    selectedAnswer = selectedAnswer,
                    isAnswerSubmitted = isAnswerSubmitted,
                    isAnswerCorrect = selectedAnswer == sampleQuestion.bonneReponse
                ),
                onSelectOption = { option ->
                    if (!isAnswerSubmitted) {
                        selectedAnswer = option
                    }
                },
                onSubmitAnswer = {
                    isAnswerSubmitted = true
                },
                onNextQuestion = {
                    currentScreen = ScreenDestination.RESULTAT
                },
                onGoToResult = {
                    currentScreen = ScreenDestination.RESULTAT
                },
                onBackClick = {
                    currentScreen = ScreenDestination.MATIERES
                },
                bottomBar = bottomBarComposable
            )
        }

        ScreenDestination.RESULTAT, ScreenDestination.PROGRESSION -> {
            ResultatScreen(
                uiState = ResultatUiState(
                    lastQuestionAnswerText = "B. 13",
                    isLastAnswerCorrect = true,
                    lastQuestionExplication = "On a f(5) = 2 x 5 + 3\nf(5) = 10 + 3\nf(5) = 13",
                    score = 8,
                    totalQuestions = selectedQuestionCount,
                    bonnesReponses = 8,
                    mauvaisesReponses = 2,
                    pourcentage = 80,
                    dureeFormatted = "06 min 32",
                    subjectProgressList = listOf(
                        SubjectProgressUiItem("Mathématiques", 78, 0xFF0066FF, "📐"),
                        SubjectProgressUiItem("Français", 64, 0xFFEF4444, "📕"),
                        SubjectProgressUiItem("Physique-Chimie", 82, 0xFF8B5CF6, "🧪"),
                        SubjectProgressUiItem("SVT", 71, 0xFF10B981, "🌿")
                    )
                ),
                onNextQuestion = {
                    selectedAnswer = null
                    isAnswerSubmitted = false
                    currentScreen = ScreenDestination.QUIZ
                },
                onRetourAccueil = {
                    currentScreen = ScreenDestination.ACCUEIL
                },
                bottomBar = bottomBarComposable
            )
        }
    }
}

@Composable
fun KaranBottomBar(
    currentDestination: ScreenDestination,
    onDestinationSelected: (ScreenDestination) -> Unit
) {
    Surface(
        color = Color.White,
        tonalElevation = 0.dp,
        border = BorderStroke(0.5.dp, Color(0xFFE2E8F0))
    ) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp
        ) {
            NavigationBarItem(
                selected = currentDestination == ScreenDestination.ACCUEIL,
                onClick = { onDestinationSelected(ScreenDestination.ACCUEIL) },
                icon = { Icon(Icons.Default.Home, contentDescription = "Accueil") },
                label = { Text("Accueil", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF0066FF),
                    selectedTextColor = Color(0xFF0066FF),
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = Color(0xFF94A3B8),
                    unselectedTextColor = Color(0xFF94A3B8)
                )
            )

            NavigationBarItem(
                selected = currentDestination == ScreenDestination.MATIERES,
                onClick = { onDestinationSelected(ScreenDestination.MATIERES) },
                icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Matières") },
                label = { Text("Matières", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF0066FF),
                    selectedTextColor = Color(0xFF0066FF),
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = Color(0xFF94A3B8),
                    unselectedTextColor = Color(0xFF94A3B8)
                )
            )

            NavigationBarItem(
                selected = currentDestination == ScreenDestination.QUIZ,
                onClick = { onDestinationSelected(ScreenDestination.QUIZ) },
                icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Quiz") },
                label = { Text("Quiz", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF0066FF),
                    selectedTextColor = Color(0xFF0066FF),
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = Color(0xFF94A3B8),
                    unselectedTextColor = Color(0xFF94A3B8)
                )
            )

            NavigationBarItem(
                selected = currentDestination == ScreenDestination.RESULTAT,
                onClick = { onDestinationSelected(ScreenDestination.RESULTAT) },
                icon = { Icon(Icons.Default.Star, contentDescription = "Résultats") },
                label = { Text("Résultats", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF0066FF),
                    selectedTextColor = Color(0xFF0066FF),
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = Color(0xFF94A3B8),
                    unselectedTextColor = Color(0xFF94A3B8)
                )
            )


        }
    }
}
