@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.karan.views

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.karan.models.Question
import com.example.karan.viewmodels.QuizUiState
import com.example.karan.viewmodels.QuizViewModel

/**
 * Écran principal du quiz.
 *
 * Le ViewModel contient toute la logique du quiz.
 * Cet écran se contente d'afficher l'état et de transmettre
 * les actions de l'utilisateur au ViewModel.
 */
@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onRetourMatieres: () -> Unit,
    onAfficherResultat: (QuizUiState.Finished) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {

        QuizUiState.Loading -> {
            QuizLoadingScreen()
        }

        QuizUiState.Empty -> {
            QuizEmptyScreen(
                onRetourMatieres = onRetourMatieres
            )
        }

        is QuizUiState.Error -> {
            QuizErrorScreen(
                message = state.message,
                onRetourMatieres = onRetourMatieres,
                onReessayer = viewModel::chargerQuiz
            )
        }

        is QuizUiState.Ready -> {
            QuizContent(
                state = state,
                onRetourMatieres = onRetourMatieres,
                onSelectionnerReponse = viewModel::selectionnerReponse,
                onQuestionSuivante = viewModel::questionSuivante
            )
        }

        is QuizUiState.Finished -> {
            QuizFinishedScreen(
                state = state,
                onAfficherResultat = {
                    onAfficherResultat(state)
                }
            )
        }
    }
}

/* ============================================================
 * CONTENU DU QUIZ
 * ============================================================ */

@Composable
private fun QuizContent(
    state: QuizUiState.Ready,
    onRetourMatieres: () -> Unit,
    onSelectionnerReponse: (String) -> Unit,
    onQuestionSuivante: () -> Unit
) {
    Scaffold(
        topBar = {
            QuizTopBar(
                onRetour = onRetourMatieres
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            /* Progression */

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Question ${state.indexActuel + 1}/${state.totalQuestions}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                TimerBadge(
                    secondes = state.tempsRestantSecondes
                )
            }

            LinearProgressIndicator(
                progress = { state.progression },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            /* Question */

            QuestionCard(
                question = state.question
            )

            /* Réponses */

            AnswersSection(
                question = state.question,
                reponseSelectionnee = state.reponseSelectionnee,
                correctionAffichee = state.correctionAffichee,
                estCorrecte = state.estCorrecte,
                onSelectionnerReponse = onSelectionnerReponse
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            /* Bouton suivant */

            Button(
                onClick = onQuestionSuivante,
                enabled = state.reponseSelectionnee != null,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (state.estDerniereQuestion) {
                        "Terminer le quiz"
                    } else {
                        "Question suivante"
                    },
                    fontSize = 16.sp
                )
            }
        }
    }
}

/* ============================================================
 * TOP BAR
 * ============================================================ */

@Composable
private fun QuizTopBar(
    onRetour: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Quiz",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onRetour
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Retour"
                )
            }
        }
    )
}

/* ============================================================
 * CHRONOMÈTRE
 * ============================================================ */

@Composable
private fun TimerBadge(
    secondes: Int
) {
    val minutes = secondes / 60
    val secondesRestantes = secondes % 60

    val tempsFormate =
        String.format(
            "%02d:%02d",
            minutes,
            secondesRestantes
        )

    Row(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Spacer(
            modifier = Modifier.width(6.dp)
        )

        Text(
            text = tempsFormate,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/* ============================================================
 * QUESTION
 * ============================================================ */

@Composable
private fun QuestionCard(
    question: Question
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = question.enonce,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 26.sp
            )

            if (question.chapitre.isNotBlank()) {

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = question.chapitre,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/* ============================================================
 * RÉPONSES
 * ============================================================ */

@Composable
private fun AnswersSection(
    question: Question,
    reponseSelectionnee: String?,
    correctionAffichee: Boolean,
    estCorrecte: Boolean?,
    onSelectionnerReponse: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        AnswerCard(
            lettre = "A",
            texte = question.optionA,
            selected = reponseSelectionnee == "A",
            correct = correctionAffichee &&
                    question.reponseCorrecte.equals(
                        "A",
                        ignoreCase = true
                    ),
            incorrect = correctionAffichee &&
                    reponseSelectionnee == "A" &&
                    estCorrecte == false,
            enabled = !correctionAffichee,
            onClick = {
                onSelectionnerReponse("A")
            }
        )

        AnswerCard(
            lettre = "B",
            texte = question.optionB,
            selected = reponseSelectionnee == "B",
            correct = correctionAffichee &&
                    question.reponseCorrecte.equals(
                        "B",
                        ignoreCase = true
                    ),
            incorrect = correctionAffichee &&
                    reponseSelectionnee == "B" &&
                    estCorrecte == false,
            enabled = !correctionAffichee,
            onClick = {
                onSelectionnerReponse("B")
            }
        )

        AnswerCard(
            lettre = "C",
            texte = question.optionC,
            selected = reponseSelectionnee == "C",
            correct = correctionAffichee &&
                    question.reponseCorrecte.equals(
                        "C",
                        ignoreCase = true
                    ),
            incorrect = correctionAffichee &&
                    reponseSelectionnee == "C" &&
                    estCorrecte == false,
            enabled = !correctionAffichee,
            onClick = {
                onSelectionnerReponse("C")
            }
        )

        AnswerCard(
            lettre = "D",
            texte = question.optionD,
            selected = reponseSelectionnee == "D",
            correct = correctionAffichee &&
                    question.reponseCorrecte.equals(
                        "D",
                        ignoreCase = true
                    ),
            incorrect = correctionAffichee &&
                    reponseSelectionnee == "D" &&
                    estCorrecte == false,
            enabled = !correctionAffichee,
            onClick = {
                onSelectionnerReponse("D")
            }
        )
    }
}

/* ============================================================
 * CARTE D'UNE RÉPONSE
 * ============================================================ */

@Composable
private fun AnswerCard(
    lettre: String,
    texte: String,
    selected: Boolean,
    correct: Boolean,
    incorrect: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor =
        when {
            correct ->
                MaterialTheme.colorScheme.primaryContainer

            incorrect ->
                MaterialTheme.colorScheme.errorContainer

            selected ->
                MaterialTheme.colorScheme.secondaryContainer

            else ->
                MaterialTheme.colorScheme.surface
        }

    val borderColor =
        when {
            correct ->
                MaterialTheme.colorScheme.primary

            incorrect ->
                MaterialTheme.colorScheme.error

            selected ->
                MaterialTheme.colorScheme.secondary

            else ->
                MaterialTheme.colorScheme.outline
        }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        color = borderColor,
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = lettre,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Text(
                text = texte,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                lineHeight = 23.sp
            )

            if (correct) {

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Bonne réponse"
                )
            }

            if (incorrect) {

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Mauvaise réponse"
                )
            }
        }
    }
}

/* ============================================================
 * CHARGEMENT
 * ============================================================ */

@Composable
private fun QuizLoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CircularProgressIndicator()

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Chargement du quiz...",
                fontSize = 16.sp
            )
        }
    }
}

/* ============================================================
 * AUCUNE QUESTION
 * ============================================================ */

@Composable
private fun QuizEmptyScreen(
    onRetourMatieres: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Aucune question disponible.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Aucune donnée pour le moment.",
                fontSize = 15.sp
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            OutlinedButton(
                onClick = onRetourMatieres
            ) {
                Text(
                    text = "Retour aux matières",
                    fontSize = 16.sp
                )
            }
        }
    }
}

/* ============================================================
 * ERREUR
 * ============================================================ */

@Composable
private fun QuizErrorScreen(
    message: String,
    onRetourMatieres: () -> Unit,
    onReessayer: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Une erreur est survenue.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = message,
                fontSize = 15.sp
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedButton(
                    onClick = onRetourMatieres
                ) {
                    Text(
                        text = "Retour",
                        fontSize = 16.sp
                    )
                }

                Button(
                    onClick = onReessayer
                ) {
                    Text(
                        text = "Réessayer",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

/* ============================================================
 * FIN DU QUIZ
 * ============================================================ */

@Composable
private fun QuizFinishedScreen(
    state: QuizUiState.Finished,
    onAfficherResultat: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Quiz terminé !",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "${state.score} / ${state.totalQuestions}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "${state.pourcentage} %",
                fontSize = 20.sp
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            if (state.tempsEcoule) {

                Text(
                    text = "Le temps est écoulé.",
                    fontSize = 15.sp
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Button(
                onClick = onAfficherResultat,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Voir le résultat",
                    fontSize = 16.sp
                )
            }
        }
    }
}
