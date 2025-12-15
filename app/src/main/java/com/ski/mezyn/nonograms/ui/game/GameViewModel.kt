package com.ski.mezyn.nonograms.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ski.mezyn.nonograms.data.model.CellState
import com.ski.mezyn.nonograms.data.model.GameState
import com.ski.mezyn.nonograms.data.model.Puzzle
import com.ski.mezyn.nonograms.data.repository.PuzzleRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class InputMode {
    FILL,   // Tap to fill cells
    MARK    // Tap to mark cells as empty (X)
}

data class GameUiState(
    val puzzle: Puzzle? = null,
    val gameState: GameState? = null,
    val inputMode: InputMode = InputMode.FILL,
    val canUndo: Boolean = false,
    val canRedo: Boolean = false,
    val showCompletionDialog: Boolean = false
)

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private val undoStack = mutableListOf<GameState>()
    private val redoStack = mutableListOf<GameState>()
    private var timerJob: Job? = null

    companion object {
        private const val MAX_UNDO_STACK_SIZE = 50
    }

    fun loadPuzzle(puzzleId: String) {
        val puzzle = PuzzleRepository.getPuzzleById(puzzleId) ?: return

        val initialGameState = GameState.empty(
            puzzleId = puzzle.id,
            gridSize = puzzle.gridSize
        )

        _uiState.value = GameUiState(
            puzzle = puzzle,
            gameState = initialGameState,
            inputMode = InputMode.FILL,
            canUndo = false,
            canRedo = false
        )

        startTimer()
    }

    fun toggleCell(row: Int, col: Int) {
        val currentState = _uiState.value.gameState ?: return
        val currentMode = _uiState.value.inputMode

        // Save current state to undo stack before modification
        saveStateSnapshot()

        // Create new grid with toggled cell
        val newGrid = currentState.currentGrid.mapIndexed { r, rowCells ->
            if (r == row) {
                rowCells.mapIndexed { c, cellState ->
                    if (c == col) {
                        when (currentMode) {
                            InputMode.FILL -> {
                                // Toggle between EMPTY and FILLED
                                if (cellState == CellState.FILLED || cellState == CellState.ERROR) {
                                    CellState.EMPTY
                                } else {
                                    CellState.FILLED
                                }
                            }
                            InputMode.MARK -> {
                                // Toggle between EMPTY and MARKED
                                if (cellState == CellState.MARKED) {
                                    CellState.EMPTY
                                } else {
                                    CellState.MARKED
                                }
                            }
                        }
                    } else {
                        cellState
                    }
                } else {
                    rowCells
                }
            }
        }

        val newGameState = currentState.copy(currentGrid = newGrid)
        _uiState.value = _uiState.value.copy(
            gameState = newGameState,
            canUndo = true,
            canRedo = false
        )

        // Clear redo stack when new action is performed
        redoStack.clear()

        // Check if puzzle is completed after cell toggle
        checkCompletion()
    }

    fun setInputMode(mode: InputMode) {
        _uiState.value = _uiState.value.copy(inputMode = mode)
    }

    fun undo() {
        if (undoStack.isEmpty()) return

        val currentState = _uiState.value.gameState ?: return

        // Push current state to redo stack
        redoStack.add(currentState)

        // Pop from undo stack
        val previousState = undoStack.removeLast()

        _uiState.value = _uiState.value.copy(
            gameState = previousState,
            canUndo = undoStack.isNotEmpty(),
            canRedo = true
        )
    }

    fun redo() {
        if (redoStack.isEmpty()) return

        val currentState = _uiState.value.gameState ?: return

        // Push current state to undo stack
        undoStack.add(currentState)

        // Pop from redo stack
        val nextState = redoStack.removeLast()

        _uiState.value = _uiState.value.copy(
            gameState = nextState,
            canUndo = true,
            canRedo = redoStack.isNotEmpty()
        )
    }

    fun checkForMistakes() {
        val puzzle = _uiState.value.puzzle ?: return
        val currentState = _uiState.value.gameState ?: return

        var mistakeCount = 0

        // Check filled cells against solution and mark errors
        val newGrid = currentState.currentGrid.mapIndexed { row, rowCells ->
            rowCells.mapIndexed { col, cellState ->
                val shouldBeFilled = puzzle.solution[row][col]

                when (cellState) {
                    CellState.FILLED -> {
                        if (!shouldBeFilled) {
                            mistakeCount++
                            CellState.ERROR
                        } else {
                            cellState
                        }
                    }
                    CellState.ERROR -> {
                        // Keep error state
                        if (!shouldBeFilled) {
                            mistakeCount++
                        }
                        cellState
                    }
                    else -> cellState
                }
            }
        }

        val newGameState = currentState.copy(
            currentGrid = newGrid,
            mistakes = mistakeCount
        )

        _uiState.value = _uiState.value.copy(gameState = newGameState)
    }

    fun clearGrid() {
        val puzzle = _uiState.value.puzzle ?: return

        saveStateSnapshot()

        val emptyGrid = List(puzzle.gridSize) { List(puzzle.gridSize) { CellState.EMPTY } }
        val currentState = _uiState.value.gameState ?: return

        val newGameState = currentState.copy(
            currentGrid = emptyGrid,
            mistakes = 0
        )

        _uiState.value = _uiState.value.copy(
            gameState = newGameState,
            canUndo = true
        )

        redoStack.clear()
    }

    private fun checkCompletion() {
        val puzzle = _uiState.value.puzzle ?: return
        val currentState = _uiState.value.gameState ?: return

        // Check if all cells match the solution
        val isComplete = currentState.currentGrid.withIndex().all { (row, rowCells) ->
            rowCells.withIndex().all { (col, cellState) ->
                val shouldBeFilled = puzzle.solution[row][col]
                when (cellState) {
                    CellState.FILLED -> shouldBeFilled
                    CellState.EMPTY, CellState.MARKED -> !shouldBeFilled
                    CellState.ERROR -> false
                }
            }
        }

        if (isComplete && !currentState.isCompleted) {
            stopTimer()
            val newGameState = currentState.copy(isCompleted = true)
            _uiState.value = _uiState.value.copy(
                gameState = newGameState,
                showCompletionDialog = true
            )
        }
    }

    fun dismissCompletionDialog() {
        _uiState.value = _uiState.value.copy(showCompletionDialog = false)
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000) // Update every second
                val currentState = _uiState.value.gameState ?: return@launch

                if (!currentState.isPaused && !currentState.isCompleted) {
                    val newGameState = currentState.copy(
                        elapsedTimeMillis = currentState.elapsedTimeMillis + 1000
                    )
                    _uiState.value = _uiState.value.copy(gameState = newGameState)
                }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun pauseTimer() {
        val currentState = _uiState.value.gameState ?: return
        val newGameState = currentState.copy(isPaused = true)
        _uiState.value = _uiState.value.copy(gameState = newGameState)
    }

    fun resumeTimer() {
        val currentState = _uiState.value.gameState ?: return
        val newGameState = currentState.copy(isPaused = false)
        _uiState.value = _uiState.value.copy(gameState = newGameState)
    }

    private fun saveStateSnapshot() {
        val currentState = _uiState.value.gameState ?: return

        // Limit undo stack size
        if (undoStack.size >= MAX_UNDO_STACK_SIZE) {
            undoStack.removeAt(0)
        }

        undoStack.add(currentState.copy())
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}
