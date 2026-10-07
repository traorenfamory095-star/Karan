@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.karan.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.karan.viewmodels.AccueilUiState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.karan.viewmodels.ProgressionParMatiere

private val HeaderTop = Color(0xFF0B1F4B)
private val HeaderBottom = Color(0xFF17346F)
private val PrimaryBlue = Color(0xFF0066FF)
private val TextDark = Color(0xFF0F172A)
private val TextGray = Color(0xFF64748B)

/*
 * ------------------------------------------------------------
 * ACCUEIL SCREEN
 * ------------------------------------------------------------
 *
 * L'état de l'écran vient maintenant directement
 *
 * AccueilScreen ne possède donc plus son propre
 * AccueilUiState.
 *
 * ------------------------------------------------------------
 */

@Composable
fun AccueilScreen(
    uiState: AccueilUiState,
    onExamenSelected: (String) -> Unit = {},
    onOpenMatieres: () -> Unit = {},
    onReprendreRevision: () -> Unit = {},
    onRevoirErreurs: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            AccueilTopBar()
        }
    ) { innerPadding ->

        when {
            uiState.chargement -> {
                LoadingState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            uiState.erreur != null -> {
                ErrorState(
                    message = uiState.erreur,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            else -> {
                AccueilContent(
                    uiState = uiState,
                    onExamenSelected = onExamenSelected,
                    onOpenMatieres = onOpenMatieres,
                    onReprendreRevision = onReprendreRevision,
                    onRevoirErreurs = onRevoirErreurs,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun AccueilTopBar() {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = PrimaryBlue
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(6.dp)
                    ) {
                        Text(
                            text = "🎓",
                            fontSize = 16.sp
                        )
                    }
                }

                Text(
                    text = "Karan",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = HeaderTop
        )
    )
}

@Composable
private fun AccueilContent(
    uiState: AccueilUiState,
    onExamenSelected: (String) -> Unit,
    onOpenMatieres: () -> Unit,
    onReprendreRevision: () -> Unit,
    onRevoirErreurs: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        /*
         * --------------------------------------------------
         * HEADER
         * --------------------------------------------------
         */

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            HeaderTop,
                            HeaderBottom
                        )
                    ),
                    shape = RoundedCornerShape(
                        bottomStart = 24.dp,
                        bottomEnd = 24.dp
                    )
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            GreetingHeader(
                studentName = "ODC"
            )

            ExamenSelectionCards(
                selectedExamen = uiState.examen,
                onExamenSelected = onExamenSelected
            )
        }

        /*
         * --------------------------------------------------
         * CONTENU
         * --------------------------------------------------
         */

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            ProgressionSection(
                progression = uiState.progression,
                progressionsMatieres = uiState.progressionsMatieres,
                onOpenMatieres = onOpenMatieres
            )

            ReprendreRevisionCard(
                title = uiState.derniereMatiere,
                onClick = onReprendreRevision
            )

            ErreursCard(
                count = uiState.nombreErreursARevoir,
                onClick = onRevoirErreurs
            )
        }
    }
}

@Composable
private fun GreetingHeader(
    studentName: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Text(
            text = "Bonjour,",
            fontSize = 18.sp,
            color = Color.White
        )

        Text(
            text = "$studentName 👋",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = "Prêt à atteindre tes objectifs ?",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFE2E8F0)
        )

        Text(
            text = "Choisis ton examen et commence ta révision.",
            fontSize = 14.sp,
            color = Color(0xFFCBD5E1)
        )
    }
}

@Composable
private fun ExamenSelectionCards(
    selectedExamen: String,
    onExamenSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        ExamenCard(
            examen = "BAC",
            selected = selectedExamen == "BAC",
            description = "Baccalauréat\n1ère - Terminale",
            backgroundColor = PrimaryBlue,
            onClick = {
                onExamenSelected("BAC")
            }
        )

        ExamenCard(
            examen = "BEPC",
            selected = selectedExamen == "BEPC",
            description = "3e - Diplôme\ndu premier cycle",
            backgroundColor = Color(0xFF10B981),
            onClick = {
                onExamenSelected("BEPC")
            }
        )
    }
}

@Composable
private fun RowScope.ExamenCard(
    examen: String,
    selected: Boolean,
    description: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .weight(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (selected) 4.dp else 1.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Surface(
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.25f),
                modifier = Modifier.size(40.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🎓",
                        fontSize = 20.sp
                    )
                }
            }

            Text(
                text = examen,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.9f),
                lineHeight = 18.sp
            )
        }
    }
}


@Composable
private fun ProgressionSection(
    progression: com.example.karan.viewmodels.ProgressionGenerale,
    progressionsMatieres: List<ProgressionParMatiere> = emptyList(),
    onOpenMatieres: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ma progression",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Text(
                    text = "Voir tout",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryBlue,
                    modifier = Modifier.clickable {
                        onOpenMatieres()
                    }
                )
            }

            Text(
                text = "${progression.nombreQuiz} quiz réalisés",
                fontSize = 14.sp,
                color = TextGray
            )

            Text(
                text = "${progression.reussiteMoyenne}% de réussite moyenne",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextDark
            )

            LinearProgressIndicator(
                progress = {
                    (progression.reussiteMoyenne / 100f)
                        .coerceIn(0f, 1f)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = PrimaryBlue,
                trackColor = Color(0xFFE2E8F0)
            )

            if (progressionsMatieres.isNotEmpty()) {

                Text(
                    text = "Progression par matière",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                progressionsMatieres.forEach { item ->

                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.matiere,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextDark
                            )

                            Text(
                                text = "${item.reussiteMoyenne}%",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryBlue
                            )
                        }

                        LinearProgressIndicator(
                            progress = {
                                (item.reussiteMoyenne / 100f)
                                    .coerceIn(0f, 1f)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = PrimaryBlue,
                            trackColor = Color(0xFFE2E8F0)
                        )

                        Text(
                            text = "${item.nombreQuiz} quiz réalisés",
                            fontSize = 12.sp,
                            color = TextGray
                        )
                    }
                }

            } else if (progression.nombreQuiz == 0) {

                Text(
                    text = "Aucune donnée pour le moment",
                    fontSize = 14.sp,
                    color = TextGray
                )
            }
        }
    }
}


@Composable
private fun ReprendreRevisionCard(
    title: String?,
    onClick: () -> Unit
) {
    val hasRevision = title != null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = hasRevision,
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        color = PrimaryBlue.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "📘",
                    fontSize = 22.sp
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                Text(
                    text = "Reprendre ma révision",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Text(
                    text = title ?: "Aucune révision récente",
                    fontSize = 14.sp,
                    color = TextGray
                )
            }

            if (hasRevision) {
                Icon(
                    imageVector =
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Continuer la révision",
                    tint = Color(0xFF94A3B8)
                )
            }
        }
    }
}

@Composable
private fun ErreursCard(
    count: Int,
    onClick: () -> Unit
) {
    val hasErrors = count > 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = hasErrors,
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (hasErrors) {
                Color(0xFFFFF1F2)
            } else {
                Color(0xFFF8FAFC)
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        color = if (hasErrors) {
                            Color(0xFFE11D48)
                        } else {
                            Color(0xFF94A3B8)
                        },
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (hasErrors) "⚠️" else "✓",
                    fontSize = 20.sp
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                Text(
                    text = if (hasErrors) {
                        "$count erreurs à revoir"
                    } else {
                        "Aucune erreur à revoir"
                    },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (hasErrors) {
                        Color(0xFFE11D48)
                    } else {
                        TextDark
                    }
                )

                Text(
                    text = if (hasErrors) {
                        "Des questions nécessitent une révision."
                    } else {
                        "Aucune erreur à revoir pour le moment."
                    },
                    fontSize = 14.sp,
                    color = if (hasErrors) {
                        Color(0xFFBE123C)
                    } else {
                        TextGray
                    }
                )
            }

            if (hasErrors) {
                Icon(
                    imageVector =
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Revoir les erreurs",
                    tint = Color(0xFFE11D48)
                )
            }
        }
    }
}

@Composable
private fun LoadingState(
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
private fun ErrorState(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            fontSize = 14.sp,
            color = Color(0xFFB91C1C)
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun AccueilScreenPreview() {

    val previewState = AccueilUiState(
        chargement = false,
        examen = "BAC",
        progression = com.example.karan.viewmodels.ProgressionGenerale(
            nombreQuiz = 8,
            reussiteMoyenne = 72
        ),
        progressionsMatieres = listOf(
            ProgressionParMatiere(
                matiereId = 1L,
                matiere = "Mathématiques",
                nombreQuiz = 3,
                reussiteMoyenne = 75
            ),
            ProgressionParMatiere(
                matiereId = 2L,
                matiere = "Physique",
                nombreQuiz = 2,
                reussiteMoyenne = 60
            ),
            ProgressionParMatiere(
                matiereId = 3L,
                matiere = "Chimie",
                nombreQuiz = 3,
                reussiteMoyenne = 80
            )
        ),
        dernieresSessions = emptyList(),
        derniereMatiere = "Mathématiques",
        nombreErreursARevoir = 3,
        erreur = null
    )

    AccueilScreen(
        uiState = previewState
    )
}

