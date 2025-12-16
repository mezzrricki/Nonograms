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

    // Serialize grid to string: "0,1:0,2,3:2,0,1:1..." where format is "type" or "type:colorIndex"
    // type: 0=EMPTY, 1=FILLED, 2=MARKED, 3=ERROR
    // colorIndex: 0=black, 1+=color
    private fun serializeGrid(grid: List<List<CellState>>): String {
        return grid.flatten().joinToString(",") { cellState ->
            when (cellState) {
                is CellState.Empty -> "0"
                is CellState.Filled -> if (cellState.colorIndex == 0) "1" else "1:${cellState.colorIndex}"
                is CellState.Marked -> "2"
                is CellState.Error -> if (cellState.colorIndex == 0) "3" else "3:${cellState.colorIndex}"
            }
        }
    }

    // Deserialize string back to grid
    fun deserializeGrid(serialized: String, gridSize: Int): List<List<CellState>> {
        val values = serialized.split(",")
        return values.chunked(gridSize).map { row ->
            row.map { value ->
                val parts = value.split(":")
                val type = parts[0].toInt()
                val colorIndex = if (parts.size > 1) parts[1].toInt() else 0

                when (type) {
                    0 -> CellState.Empty
                    1 -> CellState.Filled(colorIndex)
                    2 -> CellState.Marked
                    3 -> CellState.Error(colorIndex)
                    else -> CellState.Empty
                }
            }
        }
    }
}
