package com.ski.mezyn.nonograms.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.ski.mezyn.nonograms.data.model.CellState
import com.ski.mezyn.nonograms.data.model.PuzzleProgress

object ProgressRepository {
    private const val PREFS_NAME = "nonogram_progress"
    private const val KEY_BEST_TIME = "best_time_"
    private const val KEY_TIMES_COMPLETED = "times_completed_"
    private const val KEY_LAST_PLAYED = "last_played_"
    private const val KEY_SAVED_GRID = "saved_grid_"
    private const val KEY_SAVED_TIME = "saved_time_"
    private const val KEY_SAVED_MOVES = "saved_moves_"
    private const val KEY_SAVED_ERRORS = "saved_errors_"

    private lateinit var prefs: SharedPreferences

    fun initialize(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getProgress(puzzleId: String): PuzzleProgress {
        return PuzzleProgress(
            puzzleId = puzzleId,
            bestTimeMillis = prefs.getLong(KEY_BEST_TIME + puzzleId, Long.MAX_VALUE),
            timesCompleted = prefs.getInt(KEY_TIMES_COMPLETED + puzzleId, 0),
            lastPlayedMillis = prefs.getLong(KEY_LAST_PLAYED + puzzleId, 0L),
            savedGrid = prefs.getString(KEY_SAVED_GRID + puzzleId, null),
            savedTimeMillis = prefs.getLong(KEY_SAVED_TIME + puzzleId, 0L),
            savedMoves = prefs.getInt(KEY_SAVED_MOVES + puzzleId, 0),
            savedErrors = prefs.getInt(KEY_SAVED_ERRORS + puzzleId, 0)
        )
    }

    fun saveProgress(progress: PuzzleProgress) {
        prefs.edit().apply {
            putLong(KEY_BEST_TIME + progress.puzzleId, progress.bestTimeMillis)
            putInt(KEY_TIMES_COMPLETED + progress.puzzleId, progress.timesCompleted)
            putLong(KEY_LAST_PLAYED + progress.puzzleId, progress.lastPlayedMillis)

            // Save incomplete puzzle state
            if (progress.savedGrid != null) {
                putString(KEY_SAVED_GRID + progress.puzzleId, progress.savedGrid)
                putLong(KEY_SAVED_TIME + progress.puzzleId, progress.savedTimeMillis)
                putInt(KEY_SAVED_MOVES + progress.puzzleId, progress.savedMoves)
                putInt(KEY_SAVED_ERRORS + progress.puzzleId, progress.savedErrors)
            } else {
                // Clear saved progress if grid is null
                remove(KEY_SAVED_GRID + progress.puzzleId)
                remove(KEY_SAVED_TIME + progress.puzzleId)
                remove(KEY_SAVED_MOVES + progress.puzzleId)
                remove(KEY_SAVED_ERRORS + progress.puzzleId)
            }

            apply()
        }
    }

    fun updateBestTime(puzzleId: String, timeMillis: Long): Boolean {
        val currentProgress = getProgress(puzzleId)
        val isNewRecord = timeMillis < currentProgress.bestTimeMillis

        val updatedProgress = currentProgress.copy(
            bestTimeMillis = if (isNewRecord) timeMillis else currentProgress.bestTimeMillis,
            timesCompleted = currentProgress.timesCompleted + 1,
            lastPlayedMillis = System.currentTimeMillis(),
            // Clear saved progress on completion
            savedGrid = null,
            savedTimeMillis = 0L,
            savedMoves = 0,
            savedErrors = 0
        )

        saveProgress(updatedProgress)
        return isNewRecord
    }

    fun saveInProgressPuzzle(
        puzzleId: String,
        grid: List<List<CellState>>,
        timeMillis: Long,
        moves: Int,
        errors: Int
    ) {
        val currentProgress = getProgress(puzzleId)
        val serializedGrid = serializeGrid(grid)

        val updatedProgress = currentProgress.copy(
            lastPlayedMillis = System.currentTimeMillis(),
            savedGrid = serializedGrid,
            savedTimeMillis = timeMillis,
            savedMoves = moves,
            savedErrors = errors
        )

        saveProgress(updatedProgress)
    }

    fun clearSavedProgress(puzzleId: String) {
        val currentProgress = getProgress(puzzleId)
        val updatedProgress = currentProgress.copy(
            savedGrid = null,
            savedTimeMillis = 0L,
            savedMoves = 0,
            savedErrors = 0
        )
        saveProgress(updatedProgress)
    }

    // Serialize grid to string: "0,1,2,3,0,1,1..." where 0=EMPTY, 1=FILLED, 2=MARKED, 3=ERROR
    private fun serializeGrid(grid: List<List<CellState>>): String {
        return grid.flatten().joinToString(",") { cellState ->
            when (cellState) {
                CellState.EMPTY -> "0"
                CellState.FILLED -> "1"
                CellState.MARKED -> "2"
                CellState.ERROR -> "3"
            }
        }
    }

    // Deserialize string back to grid
    fun deserializeGrid(serialized: String, gridSize: Int): List<List<CellState>> {
        val values = serialized.split(",").map { it.toInt() }
        return values.chunked(gridSize).map { row ->
            row.map { value ->
                when (value) {
                    0 -> CellState.EMPTY
                    1 -> CellState.FILLED
                    2 -> CellState.MARKED
                    3 -> CellState.ERROR
                    else -> CellState.EMPTY
                }
            }
        }
    }
}
