package com.ski.mezyn.nonograms.data.model

data class GameState(
    val puzzleId: String,
    val gridSize: Int,
    val currentGrid: List<List<CellState>>,
    val mistakes: Int = 0,
    val elapsedTimeMillis: Long = 0L,
    val isCompleted: Boolean = false,
    val isPaused: Boolean = false
) {
    companion object {
        fun empty(puzzleId: String, gridSize: Int): GameState {
            val emptyGrid = List(gridSize) { List(gridSize) { CellState.EMPTY } }
            return GameState(
                puzzleId = puzzleId,
                gridSize = gridSize,
                currentGrid = emptyGrid
            )
        }
    }
}
