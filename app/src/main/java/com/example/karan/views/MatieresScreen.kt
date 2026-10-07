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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.karan.models.Fiche
import com.example.karan.models.Matiere
import com.example.karan.viewmodels.MatieresUiState
import com.example.karan.viewmodels.MatieresViewModel
import com.example.karan.viewmodels.ParametresQuiz

@Composable
fun MatieresScreen(
    viewModel: MatieresViewModel,
    onRetourAccueil: () -> Unit,
    onLancerQuiz: (ParametresQuiz) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.parametresQuiz) {
        uiState.parametresQuiz?.let { parametres ->
            onLancerQuiz(parametres)
            viewModel.consommerLancement()
        }
    }

    MatieresContent(
        uiState = uiState,
        onRetourAccueil = onRetourAccueil,
        onSelectionnerExamen = viewModel::selectionnerExamen,
        onSelectionnerMatiere = viewModel::selectionnerMatiere,
        onSelectionnerNombreQuestions =
            viewModel::selectionnerNombreQuestions,
        onPreparerQuiz = viewModel::preparerQuiz,
        onEffacerMessage = viewModel::effacerMessage
    )
}

@Composable
private fun MatieresContent(
    uiState: MatieresUiState,
    onRetourAccueil: () -> Unit,
    onSelectionnerExamen: (String) -> Unit,
    onSelectionnerMatiere: (Matiere) -> Unit,
    onSelectionnerNombreQuestions: (Int) -> Unit,
    onPreparerQuiz: () -> Unit,
    onEffacerMessage: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Matières",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onRetourAccueil
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Spacer(
                    modifier = Modifier.height(4.dp)
                )
            }

            item {
                ExamenSection(
                    examen = uiState.examen,
                    onSelectionnerExamen =
                        onSelectionnerExamen
                )
            }

            item {
                Text(
                    text = "Choisissez une matière",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            when {

                uiState.chargement -> {
                    item {
                        LoadingState()
                    }
                }

                uiState.erreur != null -> {
                    item {
                        ErrorState(
                            message = uiState.erreur
                        )
                    }
                }

                uiState.aucuneMatiere -> {
                    item {
                        EmptyState(
                            message =
                                uiState.messageMatieresVides
                                    ?: "Aucune matière disponible."
                        )
                    }
                }

                else -> {
                    items(
                        items = uiState.matieres,
                        key = { it.id }
                    ) { matiere ->

                        MatiereCard(
                            matiere = matiere,
                            selectionnee =
                                uiState.matiereSelectionnee?.id ==
                                        matiere.id,
                            onClick = {
                                onSelectionnerMatiere(matiere)
                            }
                        )
                    }
                }
            }

            if (uiState.matiereSelectionnee != null) {

                item {
                    FichesSection(
                        uiState = uiState
                    )
                }

                item {
                    QuizOptionsSection(
                        nombreQuestions =
                            uiState.nombreQuestions,
                        messageValidation =
                            uiState.messageValidation,
                        onSelectionnerNombreQuestions =
                            onSelectionnerNombreQuestions,
                        onPreparerQuiz =
                            onPreparerQuiz,
                        onEffacerMessage =
                            onEffacerMessage
                    )
                }
            }

            item {
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
        }
    }
}

@Composable
private fun ExamenSection(
    examen: String,
    onSelectionnerExamen: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = "Type d'examen",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(10.dp)
        ) {

            ExamenChip(
                texte = "BAC",
                selectionne = examen == "BAC",
                onClick = {
                    onSelectionnerExamen("BAC")
                }
            )

            ExamenChip(
                texte = "BEPC",
                selectionne = examen == "BEPC",
                onClick = {
                    onSelectionnerExamen("BEPC")
                }
            )
        }
    }
}

@Composable
private fun ExamenChip(
    texte: String,
    selectionne: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selectionne,
        onClick = onClick,
        label = {
            Text(
                text = texte,
                fontSize = 14.sp
            )
        },
        leadingIcon = if (selectionne) {
            {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        } else {
            null
        }
    )
}

@Composable
private fun MatiereCard(
    matiere: Matiere,
    selectionnee: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        border = if (selectionnee) {
            BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            null
        },
        colors = CardDefaults.cardColors(
            containerColor =
                if (selectionnee) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surface
                }
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
                    .size(48.dp)
                    .background(
                        color =
                            MaterialTheme.colorScheme
                                .secondaryContainer,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    tint =
                        MaterialTheme.colorScheme
                            .onSecondaryContainer
                )
            }

            Spacer(
                modifier = Modifier.size(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = matiere.nom,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text =
                        "${matiere.nombreChapitres} chapitres",
                    fontSize = 14.sp,
                    color =
                        MaterialTheme.colorScheme
                            .onSurfaceVariant
                )
            }

            if (selectionnee) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Matière sélectionnée",
                    tint =
                        MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun FichesSection(
    uiState: MatieresUiState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = "Fiches de révision",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        when {

            uiState.chargementFiches -> {
                LoadingState()
            }

            uiState.aucuneFiche -> {
                EmptyState(
                    message =
                        uiState.messageFichesVides
                            ?: "Aucune fiche disponible."
                )
            }

            else -> {
                uiState.fiches.forEach { fiche ->
                    FicheCard(fiche)
                }
            }
        }
    }
}

@Composable
private fun FicheCard(
    fiche: Fiche
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text(
                text = fiche.titre,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            if (fiche.chapitre.isNotBlank()) {
                Text(
                    text = fiche.chapitre,
                    fontSize = 14.sp,
                    color =
                        MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = fiche.contenu,
                fontSize = 14.sp,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun QuizOptionsSection(
    nombreQuestions: Int,
    messageValidation: String?,
    onSelectionnerNombreQuestions: (Int) -> Unit,
    onPreparerQuiz: () -> Unit,
    onEffacerMessage: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            Text(
                text = "Préparer le quiz",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Nombre de questions",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                listOf(5, 10, 20).forEach { nombre ->

                    FilterChip(
                        selected =
                            nombreQuestions == nombre,
                        onClick = {
                            onSelectionnerNombreQuestions(
                                nombre
                            )
                        },
                        label = {
                            Text(
                                text = "$nombre",
                                fontSize = 14.sp
                            )
                        }
                    )
                }
            }

            if (messageValidation != null) {

                Text(
                    text = messageValidation,
                    fontSize = 14.sp,
                    color =
                        MaterialTheme.colorScheme.error
                )

                OutlinedButton(
                    onClick = onEffacerMessage,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Fermer",
                        fontSize = 14.sp
                    )
                }
            }

            Button(
                onClick = onPreparerQuiz,
                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.size(8.dp)
                )

                Text(
                    text = "Commencer le quiz",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyState(
    message: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = message,
                fontSize = 14.sp,
                color =
                    MaterialTheme.colorScheme
                        .onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ErrorState(
    message: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.errorContainer
        )
    ) {

        Text(
            text = message,
            modifier = Modifier.padding(16.dp),
            fontSize = 14.sp,
            color =
                MaterialTheme.colorScheme
                    .onErrorContainer
        )
    }
}