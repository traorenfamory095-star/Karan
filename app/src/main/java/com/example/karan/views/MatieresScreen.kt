package com.example.karan.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MatiereUiItem(
    val id: String,
    val nom: String,
    val nombreChapitres: Int = 0,
    val colorHex: Long = 0xFF0066FF,
    val iconSymbol: String = "📐"
)

data class MatieresUiState(
    val selectedExamen: ExamenType = ExamenType.BAC,
    val matieres: List<MatiereUiItem> = listOf(
        MatiereUiItem("1", "Mathématiques", 12, 0xFF0066FF, "📐"),
        MatiereUiItem("2", "Français", 10, 0xFFEF4444, "📕"),
        MatiereUiItem("3", "Physique-Chimie", 8, 0xFF8B5CF6, "🧪"),
        MatiereUiItem("4", "SVT", 7, 0xFF10B981, "🌿"),
        MatiereUiItem("5", "Histoire-Géographie", 6, 0xFFF59E0B, "🌐"),
        MatiereUiItem("6", "Philosophie", 5, 0xFF06B6D4, "🧠")
    ),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatieresScreen(
    uiState: MatieresUiState = MatieresUiState(),
    onExamenSelected: (ExamenType) -> Unit = {},
    onStartQuiz: (matiereId: String, nombreQuestions: Int) -> Unit = { _, _ -> },
    onOpenFiches: (matiereId: String) -> Unit = {},
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    bottomBar: @Composable () -> Unit = {}
) {
    var selectedMatiereForQuiz by remember { mutableStateOf<MatiereUiItem?>(null) }
    var selectedQuestionCount by remember { mutableIntStateOf(10) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Matières",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color(0xFF0F172A)
                        )
                    }
                },
                actions = {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFE0EDFF),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(text = "🎓", fontSize = 12.sp)
                            Text(
                                text = uiState.selectedExamen.name,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0066FF)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = bottomBar
    ) { innerPadding: PaddingValues ->
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
                    ErrorMatieresView(message = uiState.errorMessage, onRetry = onRetry)
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ExamenPillSwitcher(
                            selectedExamen = uiState.selectedExamen,
                            onExamenSelected = onExamenSelected
                        )

                        if (uiState.matieres.isEmpty()) {
                            EmptyMatieresView()
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(uiState.matieres, key = { it.id }) { matiere ->
                                    MatiereCardItem(
                                        matiere = matiere,
                                        onClick = { selectedMatiereForQuiz = matiere }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    selectedMatiereForQuiz?.let { matiere ->
        ChoixQuizDialog(
            matiere = matiere,
            selectedCount = selectedQuestionCount,
            onCountSelected = { selectedQuestionCount = it },
            onStartQuiz = {
                onStartQuiz(matiere.id, selectedQuestionCount)
                selectedMatiereForQuiz = null
            },
            onOpenFiches = {
                onOpenFiches(matiere.id)
                selectedMatiereForQuiz = null
            },
            onDismiss = { selectedMatiereForQuiz = null }
        )
    }
}

@Composable
private fun ExamenPillSwitcher(
    selectedExamen: ExamenType,
    onExamenSelected: (ExamenType) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFFF1F5F9)
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(
                        color = if (selectedExamen == ExamenType.BAC) Color(0xFF0066FF) else Color.Transparent,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { onExamenSelected(ExamenType.BAC) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "BAC",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedExamen == ExamenType.BAC) Color.White else Color(0xFF64748B)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(
                        color = if (selectedExamen == ExamenType.BEPC) Color(0xFF0066FF) else Color.Transparent,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { onExamenSelected(ExamenType.BEPC) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "BEPC",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedExamen == ExamenType.BEPC) Color.White else Color(0xFF64748B)
                )
            }
        }
    }
}

@Composable
private fun MatiereCardItem(
    matiere: MatiereUiItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(matiere.colorHex).copy(alpha = 0.15f),
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = matiere.iconSymbol,
                    fontSize = 22.sp
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = matiere.nom,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "${matiere.nombreChapitres} chapitres",
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
private fun ChoixQuizDialog(
    matiere: MatiereUiItem,
    selectedCount: Int,
    onCountSelected: (Int) -> Unit,
    onStartQuiz: () -> Unit,
    onOpenFiches: () -> Unit,
    onDismiss: () -> Unit
) {
    val options = listOf(5, 10, 20)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = matiere.nom,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Combien de questions souhaites-tu réviser ?",
                    fontSize = 14.sp,
                    color = Color(0xFF64748B)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    options.forEach { count ->
                        FilterChip(
                            selected = selectedCount == count,
                            onClick = { onCountSelected(count) },
                            label = { Text("$count qst") },
                            leadingIcon = if (selectedCount == count) {
                                {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            } else null
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onStartQuiz,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066FF))
            ) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Démarrer Quiz")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onOpenFiches,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Voir les fiches")
            }
        }
    )
}

@Composable
private fun EmptyMatieresView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = Color(0xFF94A3B8),
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = "Aucune matière disponible pour le moment.",
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF64748B)
            )
        }
    }
}

@Composable
private fun ErrorMatieresView(
    message: String,
    onRetry: () -> Unit
) {
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
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Réessayer")
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun MatieresScreenPreview() {
    MatieresScreen()
}

