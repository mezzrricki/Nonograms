package com.ski.mezyn.nonograms.ui.puzzle_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ski.mezyn.nonograms.data.model.Difficulty
import com.ski.mezyn.nonograms.data.model.Puzzle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleListScreen(
    onPuzzleClick: (String) -> Unit,
    viewModel: PuzzleListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nonogram Puzzles") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Display puzzles grouped by difficulty
            Difficulty.entries.forEach { difficulty ->
                val puzzles = state.puzzlesByDifficulty[difficulty] ?: emptyList()
                if (puzzles.isNotEmpty()) {
                    item {
                        DifficultySection(
                            difficulty = difficulty,
                            puzzles = puzzles,
                            onPuzzleClick = onPuzzleClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DifficultySection(
    difficulty: Difficulty,
    puzzles: List<Puzzle>,
    onPuzzleClick: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Section Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = difficulty.displayName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            AssistChip(
                onClick = { },
                label = { Text("${difficulty.gridSize}x${difficulty.gridSize}") }
            )
        }

        // Puzzle Cards
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            puzzles.forEach { puzzle ->
                PuzzleCard(
                    puzzle = puzzle,
                    onClick = { onPuzzleClick(puzzle.id) }
                )
            }
        }
    }
}

@Composable
fun PuzzleCard(
    puzzle: Puzzle,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = puzzle.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${puzzle.gridSize}x${puzzle.gridSize} grid",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Play button indicator
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play puzzle",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
