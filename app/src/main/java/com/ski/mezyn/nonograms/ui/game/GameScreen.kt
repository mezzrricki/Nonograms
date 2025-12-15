package com.ski.mezyn.nonograms.ui.game

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ski.mezyn.nonograms.ui.game.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    puzzleId: String,
    onBackClick: () -> Unit,
    viewModel: GameViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(puzzleId) {
        viewModel.loadPuzzle(puzzleId)
    }

    val puzzle = uiState.puzzle
    val gameState = uiState.gameState

    if (puzzle == null || gameState == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nonogram") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Game Header (puzzle name, difficulty, mistakes, timer)
            GameHeader(
                puzzleName = puzzle.name,
                difficulty = puzzle.difficulty,
                mistakes = gameState.mistakes,
                elapsedTimeMillis = gameState.elapsedTimeMillis
            )

            // Nonogram Grid with Clues
            NonogramGridWithClues(
                puzzle = puzzle,
                gameState = gameState,
                onCellTap = { row, col ->
                    viewModel.toggleCell(row, col)
                },
                onDragStart = { row, col ->
                    viewModel.startDragAction(row, col)
                },
                onDragCell = { row, col, targetState ->
                    viewModel.setCellToState(row, col, targetState)
                },
                onDragEnd = {
                    viewModel.finishDragAction()
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            // Game Controls
            GameControls(
                inputMode = uiState.inputMode,
                canUndo = uiState.canUndo,
                canRedo = uiState.canRedo,
                onInputModeChange = { mode ->
                    viewModel.setInputMode(mode)
                },
                onUndo = { viewModel.undo() },
                onRedo = { viewModel.redo() },
                onCheck = { viewModel.checkForMistakes() },
                onClear = { viewModel.clearGrid() }
            )
        }

        // Completion Dialog
        if (uiState.showCompletionDialog) {
            CompletionDialog(
                puzzleName = puzzle.name,
                elapsedTimeMillis = gameState.elapsedTimeMillis,
                mistakes = gameState.mistakes,
                bestTimeMillis = uiState.bestTimeMillis,
                isNewRecord = uiState.isNewRecord,
                onDismiss = {
                    viewModel.dismissCompletionDialog()
                    onBackClick()
                }
            )
        }
    }
}

@Composable
fun CompletionDialog(
    puzzleName: String,
    elapsedTimeMillis: Long,
    mistakes: Int,
    bestTimeMillis: Long,
    isNewRecord: Boolean,
    onDismiss: () -> Unit
) {
    val minutes = (elapsedTimeMillis / 1000) / 60
    val seconds = (elapsedTimeMillis / 1000) % 60

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (isNewRecord) "New Record!" else "Puzzle Completed!",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Congratulations! You've completed '$puzzleName'!")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Time: ${String.format("%02d:%02d", minutes, seconds)}",
                    style = if (isNewRecord) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge,
                    fontWeight = if (isNewRecord) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal
                )

                // Show previous best time if not a new record
                if (!isNewRecord && bestTimeMillis != Long.MAX_VALUE) {
                    val bestMinutes = (bestTimeMillis / 1000) / 60
                    val bestSeconds = (bestTimeMillis / 1000) % 60
                    Text("Best Time: ${String.format("%02d:%02d", bestMinutes, bestSeconds)}")
                }

                Text("Mistakes: $mistakes")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Back to Puzzles")
            }
        }
    )
}
