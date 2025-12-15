package com.ski.mezyn.nonograms.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ski.mezyn.nonograms.data.model.CellState
import com.ski.mezyn.nonograms.data.model.GameState
import com.ski.mezyn.nonograms.data.model.Puzzle
import com.ski.mezyn.nonograms.data.repository.ProgressRepository
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
    val showCompletionDialog: Boolean = false,
    val bestTimeMillis: Long = Long.MAX_VALUE,
    val isNewRecord: Boolean = false
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

        // Load best time for this puzzle
        val progress = ProgressRepository.getProgress(puzzleId)

        _uiState.value = GameUiState(
            puzzle = puzzle,
            gameState = initialGameState,
            inputMode = InputMode.FILL,
            canUndo = false,
            canRedo = false,
            bestTimeMillis = progress.bestTimeMillis
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
                    when {
                        c != col -> cellState
                        currentMode == InputMode.FILL -> {
                            // Toggle between EMPTY and FILLED
                            if (cellState == CellState.FILLED || cellState == CellState.ERROR) {
                                CellState.EMPTY
                            } else {
                                CellState.FILLED
                            }
                        }
                        currentMode == InputMode.MARK -> {
                            // Toggle between EMPTY and MARKED
                            if (cellState == CellState.MARKED) {
                                CellState.EMPTY
                            } else {
                                CellState.MARKED
                            }
                        }
                        else -> cellState
                    }
                }
            } else {
                rowCells
            }
        }

        // Auto-mark completed rows and columns
        val autoMarkedGrid = autoMarkCompletedLines(newGrid)

        val newGameState = currentState.copy(currentGrid = autoMarkedGrid)
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

    fun setCellToState(row: Int, col: Int, targetState: CellState) {
        val currentState = _uiState.value.gameState ?: return

        // Create new grid with cell set to target state
        val newGrid = currentState.currentGrid.mapIndexed { r, rowCells ->
            if (r == row) {
                rowCells.mapIndexed { c, cellState ->
                    if (c == col) targetState else cellState
                }
            } else {
                rowCells
            }
        }

        // Don't auto-mark during drag - wait until drag is complete
        val newGameState = currentState.copy(currentGrid = newGrid)
        _uiState.value = _uiState.value.copy(
            gameState = newGameState,
            canUndo = true,
            canRedo = false
        )

        // Check if puzzle is completed after cell change
        checkCompletion()
    }

    fun finishDragAction() {
        val currentState = _uiState.value.gameState ?: return

        // Apply auto-marking after drag is complete
        val autoMarkedGrid = autoMarkCompletedLines(currentState.currentGrid)

        val newGameState = currentState.copy(currentGrid = autoMarkedGrid)
        _uiState.value = _uiState.value.copy(gameState = newGameState)

        // Check if puzzle is completed after auto-marking
        checkCompletion()
    }

    private fun autoMarkCompletedLines(grid: List<List<CellState>>): List<List<CellState>> {
        val puzzle = _uiState.value.puzzle ?: return grid
        var updatedGrid = grid

        // Check each row
        for (rowIndex in grid.indices) {
            if (isRowComplete(grid, rowIndex, puzzle.rowClues[rowIndex])) {
                // Mark all empty cells in this row
                updatedGrid = updatedGrid.mapIndexed { r, rowCells ->
                    if (r == rowIndex) {
                        rowCells.map { cell ->
                            if (cell == CellState.EMPTY) CellState.MARKED else cell
                        }
                    } else {
                        rowCells
                    }
                }
            }
        }

        // Check each column
        for (colIndex in 0 until puzzle.gridSize) {
            if (isColumnComplete(updatedGrid, colIndex, puzzle.columnClues[colIndex])) {
                // Mark all empty cells in this column
                updatedGrid = updatedGrid.mapIndexed { rowIndex, rowCells ->
                    rowCells.mapIndexed { c, cell ->
                        if (c == colIndex && cell == CellState.EMPTY) {
                            CellState.MARKED
                        } else {
                            cell
                        }
                    }
                }
            }
        }

        return updatedGrid
    }

    private fun isRowComplete(grid: List<List<CellState>>, rowIndex: Int, clues: List<Int>): Boolean {
        val row = grid[rowIndex]
        val filledSegments = getFilledSegments(row)
        return filledSegments == clues
    }

    private fun isColumnComplete(grid: List<List<CellState>>, colIndex: Int, clues: List<Int>): Boolean {
        val column = grid.map { it[colIndex] }
        val filledSegments = getFilledSegments(column)
        return filledSegments == clues
    }

    private fun getFilledSegments(line: List<CellState>): List<Int> {
        val segments = mutableListOf<Int>()
        var currentSegment = 0

        for (cell in line) {
            when (cell) {
                CellState.FILLED -> currentSegment++
                CellState.EMPTY, CellState.MARKED, CellState.ERROR -> {
                    if (currentSegment > 0) {
                        segments.add(currentSegment)
                        currentSegment = 0
                    }
                }
            }
        }

        // Add last segment if exists
        if (currentSegment > 0) {
            segments.add(currentSegment)
        }

        return if (segments.isEmpty()) listOf(0) else segments
    }

    fun startDragAction(row: Int, col: Int): CellState {
        // Save state snapshot at the start of drag
        saveStateSnapshot()
        redoStack.clear()

        val currentState = _uiState.value.gameState ?: return CellState.EMPTY
        val currentMode = _uiState.value.inputMode
        val currentCellState = currentState.currentGrid[row][col]

        // Determine what state to apply based on first cell and mode
        val targetState = when (currentMode) {
            InputMode.FILL -> {
                // If currently filled or error, clear it. Otherwise fill it.
                if (currentCellState == CellState.FILLED || currentCellState == CellState.ERROR) {
                    CellState.EMPTY
                } else {
                    CellState.FILLED
                }
            }
            InputMode.MARK -> {
                // If currently marked, clear it. Otherwise mark it.
                if (currentCellState == CellState.MARKED) {
                    CellState.EMPTY
                } else {
                    CellState.MARKED
                }
            }
        }

        // Apply to first cell
        setCellToState(row, col, targetState)

        return targetState
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

            // Save progress and check for new record
            val isNewRecord = ProgressRepository.updateBestTime(
                puzzleId = puzzle.id,
                timeMillis = currentState.elapsedTimeMillis
            )

            val newGameState = currentState.copy(isCompleted = true)
            _uiState.value = _uiState.value.copy(
                gameState = newGameState,
                showCompletionDialog = true,
                isNewRecord = isNewRecord
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
