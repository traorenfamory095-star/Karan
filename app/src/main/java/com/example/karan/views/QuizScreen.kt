package com.example.karan.views

import java.util.Locale
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class QuestionUiItem(
    val id: String,
    val enonce: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val bonneReponse: String,
    val explication: String,
    val matiereNom: String = "",
    val chapitreNom: String = ""
)

data class QuizUiState(
    val currentQuestionIndex: Int = 2,
    val totalQuestions: Int = 10,
    val timeRemainingSeconds: Int = 42,
    val currentQuestion: QuestionUiItem? = QuestionUiItem(
        id = "q1",
        enonce = "Quel est le résultat de f(5) = 2 x 5 + 3 ?",
        optionA = "7",
        optionB = "13",
        optionC = "10",
        optionD = "8",
        bonneReponse = "B",
        explication = "On a f(5) = 2 x 5 + 3\nf(5) = 10 + 3\nf(5) = 13",
        matiereNom = "Mathématiques",
        chapitreNom = "Fonctions"
    ),
    val selectedAnswer: String? = "B",
    val isAnswerSubmitted: Boolean = false,
    val isAnswerCorrect: Boolean? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    uiState: QuizUiState = QuizUiState(),
    onSelectOption: (String) -> Unit = {},
    onSubmitAnswer: () -> Unit = {},
    onNextQuestion: () -> Unit = {},
    onGoToResult: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    bottomBar: @Composable () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Quiz",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quitter le quiz",
                            tint = Color(0xFF0F172A)
                        )
                    }
                },
                actions = {
                    TimerPillBadge(secondsRemaining = uiState.timeRemainingSeconds)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = bottomBar
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8FAFC))
        ) {
            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF0066FF))
                    }
                }

                uiState.errorMessage != null -> {
                    ErrorQuizView(message = uiState.errorMessage, onRetry = onRetry)
                }

                uiState.currentQuestion == null -> {
                    EmptyQuestionsView(onBackClick = onBackClick)
                }

                else -> {
                    QuizContent(
                        uiState = uiState,
                        onSelectOption = onSelectOption,
                        onSubmitAnswer = onSubmitAnswer,
                        onNextQuestion = onNextQuestion,
                        onGoToResult = onGoToResult
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizContent(
    uiState: QuizUiState,
    onSelectOption: (String) -> Unit,
    onSubmitAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    onGoToResult: () -> Unit
) {
    val question = uiState.currentQuestion ?: return
    val progress = (uiState.currentQuestionIndex + 1).toFloat() / uiState.totalQuestions.coerceAtLeast(1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Counter & Progress Bar
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "${uiState.currentQuestionIndex + 1} / ${uiState.totalQuestions}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            LinearProgressIndicator(
                progress = { progress.coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = Color(0xFF0066FF),
                trackColor = Color(0xFFE2E8F0)
            )
        }

        // Category Pills (Matière > Chapitre)
        if (question.matiereNom.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFE0EDFF)
                ) {
                    Text(
                        text = "${question.matiereNom} >",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF0066FF),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                if (question.chapitreNom.isNotEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFE0EDFF)
                    ) {
                        Text(
                            text = question.chapitreNom,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF0066FF),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Question Statement
        Text(
            text = question.enonce,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF0F172A),
            lineHeight = 26.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // Options A, B, C, D
        val options = listOf(
            "A" to question.optionA,
            "B" to question.optionB,
            "C" to question.optionC,
            "D" to question.optionD
        )

        options.forEach { (key, value) ->
            QuizOptionCard(
                letter = key,
                text = value,
                isSelected = uiState.selectedAnswer == key,
                isSubmitted = uiState.isAnswerSubmitted,
                isCorrectAnswer = question.bonneReponse == key,
                onClick = {
                    if (!uiState.isAnswerSubmitted) {
                        onSelectOption(key)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Action Button
        val isLastQuestion = uiState.currentQuestionIndex >= uiState.totalQuestions - 1

        Button(
            onClick = {
                if (!uiState.isAnswerSubmitted) {
                    onSubmitAnswer()
                } else if (isLastQuestion) {
                    onGoToResult()
                } else {
                    onNextQuestion()
                }
            },
            enabled = uiState.selectedAnswer != null || uiState.isAnswerSubmitted,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066FF))
        ) {
            Text(
                text = if (!uiState.isAnswerSubmitted) "Valider" else if (isLastQuestion) "Voir les résultats" else "Question suivante",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun QuizOptionCard(
    letter: String,
    text: String,
    isSelected: Boolean,
    isSubmitted: Boolean,
    isCorrectAnswer: Boolean,
    onClick: () -> Unit
) {
    val borderColor = when {
        isSubmitted && isCorrectAnswer -> Color(0xFF10B981)
        isSubmitted && isSelected -> Color(0xFFEF4444)
        isSelected -> Color(0xFF0066FF)
        else -> Color(0xFFE2E8F0)
    }

    val containerColor = when {
        isSubmitted && isCorrectAnswer -> Color(0xFFECFDF5)
        isSubmitted && isSelected -> Color(0xFFFEF2F2)
        isSelected -> Color(0xFFF0F6FF)
        else -> Color.White
    }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isSubmitted, onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = containerColor),
        border = BorderStroke(if (isSelected || (isSubmitted && isCorrectAnswer)) 2.dp else 1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = when {
                            isSubmitted && isCorrectAnswer -> Color(0xFF10B981)
                            isSubmitted && isSelected -> Color(0xFFEF4444)
                            isSelected -> Color(0xFF0066FF)
                            else -> Color(0xFFF1F5F9)
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = letter,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        isSelected || (isSubmitted && isCorrectAnswer) -> Color.White
                        else -> Color(0xFF475569)
                    }
                )
            }

            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = Color(0xFF0F172A),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun TimerPillBadge(secondsRemaining: Int) {
    val minutes = secondsRemaining / 60
    val seconds = secondsRemaining % 60
    val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)

    Surface(
        shape = CircleShape,
        color = Color(0xFFF1F5F9),
        border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
        modifier = Modifier.padding(end = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(text = "⏱", fontSize = 14.sp)
            Text(
                text = formattedTime,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF334155)
            )
        }
    }
}

@Composable
private fun EmptyQuestionsView(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = Color(0xFF94A3B8),
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = "Aucune question disponible pour cette matière.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF64748B)
            )
            Button(onClick = onBackClick) {
                Text("Retour aux matières")
            }
        }
    }
}

@Composable
private fun ErrorQuizView(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = message,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            Button(onClick = onRetry) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Réessayer")
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun QuizScreenPreview() {
    QuizScreen()
}

