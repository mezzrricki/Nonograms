package com.ski.mezyn.nonograms.ui.puzzle_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ski.mezyn.nonograms.data.model.Difficulty
import com.ski.mezyn.nonograms.data.model.Puzzle
import com.ski.mezyn.nonograms.ui.theme.GameColors
import com.ski.mezyn.nonograms.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleListScreen(
    onPuzzleClick: (String) -> Unit,
    viewModel: PuzzleListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    // Refresh puzzle list when screen is displayed
    LaunchedEffect(Unit) {
        viewModel.refreshPuzzles()
    }

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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Display puzzles grouped by category, then difficulty
            state.puzzlesByCategory.forEach { (category, difficultiesMap) ->
                item {
                    CategorySection(
                        category = category,
                        difficultiesMap = difficultiesMap,
                        isExpanded = category.id in state.expandedCategories,
                        expandedDifficulties = state.expandedDifficulties,
                        progressMap = state.progressMap,
                        onToggleCategory = { viewModel.toggleCategory(category.id) },
                        onToggleDifficulty = { difficulty ->
                            viewModel.toggleDifficulty(category.id, difficulty)
                        },
                        onPuzzleClick = onPuzzleClick
                    )
                }
            }
        }
    }
}

@Composable
fun CategorySection(
    category: com.ski.mezyn.nonograms.data.model.Category,
    difficultiesMap: Map<Difficulty, List<Puzzle>>,
    isExpanded: Boolean,
    expandedDifficulties: Set<String>,
    progressMap: Map<String, com.ski.mezyn.nonograms.data.model.PuzzleProgress>,
    onToggleCategory: () -> Unit,
    onToggleDifficulty: (Difficulty) -> Unit,
    onPuzzleClick: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Category Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onToggleCategory),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    val totalPuzzles = difficultiesMap.values.sumOf { it.size }
                    Text(
                        text = "$totalPuzzles puzzles",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }

        // Difficulty sections (only shown when category is expanded)
        if (isExpanded) {
            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                difficultiesMap.forEach { (difficulty, puzzles) ->
                    DifficultySection(
                        categoryId = category.id,
                        difficulty = difficulty,
                        puzzles = puzzles,
                        isExpanded = "${category.id}:${difficulty.name}" in expandedDifficulties,
                        progressMap = progressMap,
                        onToggleExpand = { onToggleDifficulty(difficulty) },
                        onPuzzleClick = onPuzzleClick
                    )
                }
            }
        }
    }
}

@Composable
fun DifficultySection(
    categoryId: String,
    difficulty: Difficulty,
    puzzles: List<Puzzle>,
    isExpanded: Boolean,
    progressMap: Map<String, com.ski.mezyn.nonograms.data.model.PuzzleProgress>,
    onToggleExpand: () -> Unit,
    onPuzzleClick: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Difficulty Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onToggleExpand),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = difficulty.displayName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    AssistChip(
                        onClick = { },
                        label = { Text("${difficulty.gridSize}x${difficulty.gridSize}") }
                    )
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Puzzle Cards (only shown when expanded)
        if (isExpanded) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                puzzles.forEach { puzzle ->
                    PuzzleCard(
                        puzzle = puzzle,
                        progress = progressMap[puzzle.id],
                        onClick = { onPuzzleClick(puzzle.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun PuzzleCard(
    puzzle: Puzzle,
    progress: com.ski.mezyn.nonograms.data.model.PuzzleProgress?,
    onClick: () -> Unit
) {
    val isCompleted = progress != null && progress.timesCompleted > 0
    val hasBestTime = progress != null && progress.bestTimeMillis != Long.MAX_VALUE

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted)
                MaterialTheme.colorScheme.surfaceVariant
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Puzzle name with completion indicator
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = puzzle.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = if (isCompleted) FontWeight.Normal else FontWeight.SemiBold,
                        color = if (isCompleted)
                            MaterialTheme.colorScheme.onSurfaceVariant
                        else MaterialTheme.colorScheme.onSurface
                    )

                    if (isCompleted) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Completed",
                            tint = GameColors.Success,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Spacing.extraSmall))

                // Grid size
                Text(
                    text = "${puzzle.gridSize}×${puzzle.gridSize} grid",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Completion info
                if (isCompleted) {
                    Spacer(modifier = Modifier.height(Spacing.small))

                    // Best time
                    if (hasBestTime) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(Spacing.extraSmall),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            val minutes = (progress!!.bestTimeMillis / 1000) / 60
                            val seconds = (progress.bestTimeMillis / 1000) % 60
                            Text(
                                text = "Best: ${String.format("%02d:%02d", minutes, seconds)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Times completed
                    if (progress!!.timesCompleted > 1) {
                        Text(
                            text = "Completed ${progress.timesCompleted}x",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Play button indicator
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play puzzle",
                tint = if (isCompleted)
                    MaterialTheme.colorScheme.onSurfaceVariant
                else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
