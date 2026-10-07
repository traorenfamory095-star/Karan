package com.example.karan.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.karan.viewmodels.CorrectionItem
import com.example.karan.viewmodels.ResultatUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultatScreen(
    uiState: ResultatUiState,
    onNextQuestion: () -> Unit = {},
    onRetourAccueil: () -> Unit = {},
    onVoirPlusProgression: () -> Unit = {}
) {
    Scaffold(
        containerColor = Palette.fond,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Résultat",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Palette.texte
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onRetourAccueil
                    ) {
                        Icon(
                            imageVector =
                                Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription =
                                "Retour à l'accueil",
                            tint = Palette.texte
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Palette.carte
                )
            )
        }
    ) { innerPadding ->

        when (uiState) {

            ResultatUiState.Loading -> {
                LoadingResultatState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            ResultatUiState.Empty -> {
                EmptyResultatState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            is ResultatUiState.Error -> {
                ErrorResultatState(
                    message = uiState.message,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            is ResultatUiState.Success -> {
                ResultatContent(
                    uiState = uiState,
                    onNextQuestion = onNextQuestion,
                    onVoirPlusProgression =
                        onVoirPlusProgression,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun ResultatContent(
    uiState: ResultatUiState.Success,
    onNextQuestion: () -> Unit,
    onVoirPlusProgression: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Palette.fond)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        /*
         * --------------------------------------------------
         * RÉSUMÉ DU QUIZ
         * --------------------------------------------------
         */

        QuizResumeCard(
            matiere = uiState.matiere,
            examen = uiState.examen,
            score = uiState.score,
            totalQuestions = uiState.totalQuestions,
            pourcentage = uiState.pourcentage
        )

        /*
         * --------------------------------------------------
         * RÉCAPITULATIF
         * --------------------------------------------------
         */

        RecapCard(
            bonnesReponses = uiState.bonnesReponses,
            mauvaisesReponses = uiState.mauvaisesReponses,
            pourcentage = uiState.pourcentage,
            dureeSecondes = uiState.dureeSecondes
        )

        /*
         * --------------------------------------------------
         * CORRECTION
         * --------------------------------------------------
         */

        if (uiState.correctionDisponible) {
            CorrectionCard(
                correction = uiState.correction
            )
        }

        /*
         * --------------------------------------------------
         * PROGRESSION
         * --------------------------------------------------
         */

        ProgressionCard(
            nombreQuiz = uiState.progression.nombreQuiz,
            reussiteMoyenne =
                uiState.progression.reussiteMoyenne,
            onVoirPlusProgression =
                onVoirPlusProgression
        )

        /*
         * --------------------------------------------------
         * ACTION
         * --------------------------------------------------
         */

        Button(
            onClick = onNextQuestion,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0066FF)
            )
        ) {
            Text(
                text = "Continuer",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/*
 * ============================================================
 * RÉSUMÉ
 * ============================================================
 */

@Composable
private fun QuizResumeCard(
    matiere: String,
    examen: String,
    score: Int,
    totalQuestions: Int,
    pourcentage: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Palette.carte
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = matiere,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Palette.texte
            )

            Text(
                text = examen,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Palette.texteDiscret
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "$score / $totalQuestions",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0066FF)
            )

            Text(
                text = "$pourcentage % de réussite",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Palette.texteSecondaire
            )

            LinearProgressIndicator(
                progress = {
                    (pourcentage / 100f)
                        .coerceIn(0f, 1f)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = Color(0xFF0066FF),
                trackColor = Palette.piste
            )
        }
    }
}

/*
 * ============================================================
 * RÉCAPITULATIF
 * ============================================================
 */

@Composable
private fun RecapCard(
    bonnesReponses: Int,
    mauvaisesReponses: Int,
    pourcentage: Int,
    dureeSecondes: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Palette.carte
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Récapitulatif de la session",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Palette.texte
            )

            RecapRow(
                iconEmoji = "🟢",
                label = "Bonnes réponses",
                value = bonnesReponses.toString()
            )

            RecapRow(
                iconEmoji = "🔴",
                label = "Mauvaises réponses",
                value = mauvaisesReponses.toString()
            )

            RecapRow(
                iconEmoji = "⭐",
                label = "Score",
                value = "$pourcentage %"
            )

            RecapRow(
                iconEmoji = "⏱️",
                label = "Durée",
                value = formatDuree(dureeSecondes)
            )
        }
    }
}

/*
 * ============================================================
 * CORRECTION
 * ============================================================
 */

@Composable
private fun CorrectionCard(
    correction: List<CorrectionItem>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Palette.carte
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            Text(
                text = "Correction",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Palette.texte
            )

            correction.forEachIndexed { index, item ->

                CorrectionItemView(
                    index = index,
                    item = item
                )

                if (index < correction.lastIndex) {
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CorrectionItemView(
    index: Int,
    item: CorrectionItem
) {
    val estCorrecte =
        item.reponseDonnee == item.bonneReponse

    Column(
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Surface(
                shape = CircleShape,
                color = if (estCorrecte) {
                    Color(0xFF10B981)
                } else {
                    Color(0xFFEF4444)
                },
                modifier = Modifier.size(28.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (estCorrecte) {
                            Icons.Default.Check
                        } else {
                            Icons.Default.Close
                        },
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Text(
                text = "Question ${index + 1}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Palette.texte
            )
        }

        Text(
            text = item.enonce,
            fontSize = 14.sp,
            color = Palette.texteSecondaire,
            lineHeight = 21.sp
        )

        Text(
            text = "Ta réponse : ${
                item.reponseDonnee ?: "Aucune réponse"
            }",
            fontSize = 14.sp,
            color = Palette.texteSecondaire
        )

        Text(
            text = "Bonne réponse : ${item.bonneReponse}",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF10B981)
        )

        if (item.explication.isNotBlank()) {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = Palette.encadre,
                border = BorderStroke(
                    width = 1.dp,
                    color = Palette.bordureEncadre
                )
            ) {

                Text(
                    text = item.explication,
                    modifier = Modifier.padding(12.dp),
                    fontSize = 14.sp,
                    color = Palette.texteSecondaire,
                    lineHeight = 21.sp
                )
            }
        }
    }
}

/*
 * ============================================================
 * PROGRESSION
 * ============================================================
 */

@Composable
private fun ProgressionCard(
    nombreQuiz: Int,
    reussiteMoyenne: Int,
    onVoirPlusProgression: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Palette.carte
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Ma progression",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Palette.texte
            )

            Text(
                text = "$nombreQuiz quiz réalisés",
                fontSize = 14.sp,
                color = Palette.texteDiscret
            )

            Text(
                text = "$reussiteMoyenne% de réussite moyenne",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Palette.texte
            )

            LinearProgressIndicator(
                progress = {
                    (reussiteMoyenne / 100f)
                        .coerceIn(0f, 1f)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = Color(0xFF0066FF),
                trackColor = Palette.piste
            )

            Text(
                text = "Voir plus",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0066FF),
                modifier = Modifier.clickable {
                    onVoirPlusProgression()
                }
            )
        }
    }
}

/*
 * ============================================================
 * PETITS COMPOSANTS
 * ============================================================
 */

@Composable
private fun RecapRow(
    iconEmoji: String,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = iconEmoji,
                fontSize = 15.sp
            )

            Text(
                text = label,
                fontSize = 14.sp,
                color = Palette.texteSecondaire
            )
        }

        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Palette.texte
        )
    }
}

@Composable
private fun LoadingResultatState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyResultatState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "Aucune donnée pour le moment",
            fontSize = 14.sp,
            color = Palette.texteDiscret
        )
    }
}

@Composable
private fun ErrorResultatState(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = message,
            modifier = Modifier.padding(24.dp),
            fontSize = 14.sp,
            color = Color(0xFFB91C1C)
        )
    }
}

private fun formatDuree(
    secondes: Int
): String {

    val minutes = secondes / 60
    val secondesRestantes = secondes % 60

    return "%02d min %02d".format(
        minutes,
        secondesRestantes
    )
}

private object Palette {

    val fond: Color
        @Composable get() =
            if (isSystemInDarkTheme()) Color(0xFF0B1220) else Color(0xFFF8FAFC)

    val carte: Color
        @Composable get() =
            if (isSystemInDarkTheme()) Color(0xFF162033) else Color(0xFFFFFFFF)

    val texte: Color
        @Composable get() =
            if (isSystemInDarkTheme()) Color(0xFFF1F5F9) else Color(0xFF0F172A)

    val texteSecondaire: Color
        @Composable get() =
            if (isSystemInDarkTheme()) Color(0xFFCBD5E1) else Color(0xFF475569)

    val texteDiscret: Color
        @Composable get() =
            if (isSystemInDarkTheme()) Color(0xFF94A3B8) else Color(0xFF64748B)

    val piste: Color
        @Composable get() =
            if (isSystemInDarkTheme()) Color(0xFF334155) else Color(0xFFE2E8F0)

    val encadre: Color
        @Composable get() =
            if (isSystemInDarkTheme()) Color(0xFF1E2A44) else Color(0xFFF0F6FF)

    val bordureEncadre: Color
        @Composable get() =
            if (isSystemInDarkTheme()) Color(0xFF2F4270) else Color(0xFFD0E1FD)
}