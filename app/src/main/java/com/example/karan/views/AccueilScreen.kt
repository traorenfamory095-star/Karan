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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ExamenType { BAC, BEPC }

private val HeaderTop = Color(0xFF0B1F4B)
private val HeaderBottom = Color(0xFF17346F)

data class SubjectProgressItem(
    val id: String,
    val nom: String,
    val progress: Float,
    val percentageText: String,
    val colorHex: Long = 0xFF0066FF,
    val iconSymbol: String = "📐"
)

data class AccueilUiState(
    val studentName: String = "Groupe-10",
    val selectedExamen: ExamenType = ExamenType.BAC,
    val matieres: List<MatiereUiItem> = listOf(
        MatiereUiItem("1", "Mathématiques", 12, 0xFF0066FF, "📐"),
        MatiereUiItem("2", "Français", 10, 0xFFEF4444, "📕"),
        MatiereUiItem("3", "Physique-Chimie", 8, 0xFF8B5CF6, "🧪"),
        MatiereUiItem("4", "SVT", 7, 0xFF10B981, "🌿"),
        MatiereUiItem("5", "Histoire-Géographie", 6, 0xFFF59E0B, "🌐"),
        MatiereUiItem("6", "Philosophie", 5, 0xFF06B6D4, "🧠")
    ),
    val subjectProgressList: List<SubjectProgressItem> = listOf(
        SubjectProgressItem("1", "Mathématiques", 0.78f, "78%", 0xFF0066FF, "📐"),
        SubjectProgressItem("2", "Français", 0.65f, "65%", 0xFFEF4444, "📕"),
        SubjectProgressItem("3", "Physique-Chimie", 0.72f, "72%", 0xFF8B5CF6, "🧪"),
        SubjectProgressItem("4", "SVT", 0.58f, "58%", 0xFF10B981, "🌿")
    ),
    val lastRevisionTitle: String = "Mathématiques - Chapitre 3",
    val countErrorsToReview: Int = 5,
    val errorsSubjectsText: String = "Mathématiques, Physique-Chimie...",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccueilScreen(
    uiState: AccueilUiState = AccueilUiState(),
    onExamenSelected: (ExamenType) -> Unit = {},
    onOpenMatieres: () -> Unit = {},
    onMatiereClick: (MatiereUiItem) -> Unit = {},
    onReprendreRevision: () -> Unit = {},
    onRevoirErreurs: () -> Unit = {},
    bottomBar: @Composable () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { AccueilTopBarTitle() },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Paramètres",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = HeaderTop)
            )
        },
        bottomBar = bottomBar
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Zone bleue : salutation + cartes BAC / BEPC
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(listOf(HeaderTop, HeaderBottom)),
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GreetingHeader(studentName = uiState.studentName)

                ExamenSelectionCards(
                    selectedExamen = uiState.selectedExamen,
                    onExamenSelected = onExamenSelected
                )
            }

            // Reste de la page
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MatieresAccueilSection(
                    matieres = uiState.matieres,
                    onOpenMatieres = onOpenMatieres,
                    onMatiereClick = onMatiereClick
                )

                ProgressionSection(
                    progressList = uiState.subjectProgressList,
                    onOpenMatieres = onOpenMatieres
                )

                ReprendreRevisionCard(
                    title = uiState.lastRevisionTitle,
                    onClick = onReprendreRevision
                )

                ErreursCard(
                    count = uiState.countErrorsToReview,
                    subjectsText = uiState.errorsSubjectsText,
                    onClick = onRevoirErreurs
                )
            }
        }
    }
}

@Composable
private fun AccueilTopBarTitle() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFF0066FF)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(6.dp)
            ) {
                Text(text = "🎓", fontSize = 16.sp)
            }
        }
        Text(
            text = "Révision BAC / BEPC",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun GreetingHeader(studentName: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = "Bonjour,", fontSize = 18.sp, color = Color.White)
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
            fontSize = 13.sp,
            color = Color(0xFFCBD5E1)
        )
    }
}

@Composable
private fun ExamenSelectionCards(
    selectedExamen: ExamenType,
    onExamenSelected: (ExamenType) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
                .clickable { onExamenSelected(ExamenType.BAC) },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0066FF))
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
                    Box(contentAlignment = Alignment.Center) {
                        Text("🎓", fontSize = 20.sp)
                    }
                }
                Text(
                    text = "BAC",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Baccalauréat\n(1ère - Terminale)",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    lineHeight = 15.sp
                )
            }
        }

        Card(
            modifier = Modifier
                .weight(1f)
                .clickable { onExamenSelected(ExamenType.BEPC) },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF10B981))
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
                    Box(contentAlignment = Alignment.Center) {
                        Text("🎓", fontSize = 20.sp)
                    }
                }
                Text(
                    text = "BEPC",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "3e - Diplôme d'études\ndu premier cycle",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    lineHeight = 15.sp
                )
            }
        }
    }
}

@Composable
private fun ProgressionSection(
    progressList: List<SubjectProgressItem>,
    onOpenMatieres: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "Voir tout >",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF0066FF),
                    modifier = Modifier.clickable { onOpenMatieres() }
                )
            }

            progressList.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                color = Color(item.colorHex).copy(alpha = 0.15f),
                                shape = RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = item.iconSymbol, fontSize = 16.sp)
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = item.nom,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF0F172A)
                            )
                            Text(
                                text = item.percentageText,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        }
                        LinearProgressIndicator(
                            progress = { item.progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = Color(item.colorHex),
                            trackColor = Color(0xFFE2E8F0)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReprendreRevisionCard(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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
                        color = Color(0xFF0066FF).copy(alpha = 0.12f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "📘", fontSize = 22.sp)
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "Reprendre ma révision",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = title,
                    fontSize = 13.sp,
                    color = Color(0xFF64748B)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xFF94A3B8)
            )
        }
    }
}

@Composable
private fun ErreursCard(count: Int, subjectsText: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F2)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
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
                        color = Color(0xFFE11D48),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "⚠️", fontSize = 20.sp)
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "$count erreurs à revoir",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE11D48)
                )
                Text(
                    text = subjectsText,
                    fontSize = 13.sp,
                    color = Color(0xFFBE123C)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xFFE11D48)
            )
        }
    }
}

@Composable
private fun MatieresAccueilSection(
    matieres: List<MatiereUiItem>,
    onOpenMatieres: () -> Unit,
    onMatiereClick: (MatiereUiItem) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "📚", fontSize = 18.sp)
                    Text(
                        text = "Matières de révision",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                }
                Text(
                    text = "Voir tout >",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF0066FF),
                    modifier = Modifier.clickable { onOpenMatieres() }
                )
            }

            val chunkedMatieres = matieres.chunked(2)
            chunkedMatieres.forEach { rowMatieres ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowMatieres.forEach { matiere ->
                        MatiereGridCard(
                            matiere = matiere,
                            modifier = Modifier.weight(1f),
                            onClick = { onMatiereClick(matiere) }
                        )
                    }
                    if (rowMatieres.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun MatiereGridCard(
    matiere: MatiereUiItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = Color(matiere.colorHex).copy(alpha = 0.15f),
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = matiere.iconSymbol, fontSize = 18.sp)
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = matiere.nom,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A),
                    maxLines = 1
                )
                Text(
                    text = "${matiere.nombreChapitres} chapitres",
                    fontSize = 11.sp,
                    color = Color(0xFF64748B)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AccueilScreenPreview() {
    AccueilScreen()
}
