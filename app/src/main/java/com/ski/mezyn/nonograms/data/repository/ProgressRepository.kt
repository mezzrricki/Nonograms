package com.ski.mezyn.nonograms.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.ski.mezyn.nonograms.data.model.PuzzleProgress

object ProgressRepository {
    private const val PREFS_NAME = "nonogram_progress"
    private const val KEY_BEST_TIME = "best_time_"
    private const val KEY_TIMES_COMPLETED = "times_completed_"
    private const val KEY_LAST_PLAYED = "last_played_"

    private lateinit var prefs: SharedPreferences

    fun initialize(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getProgress(puzzleId: String): PuzzleProgress {
        return PuzzleProgress(
            puzzleId = puzzleId,
            bestTimeMillis = prefs.getLong(KEY_BEST_TIME + puzzleId, Long.MAX_VALUE),
            timesCompleted = prefs.getInt(KEY_TIMES_COMPLETED + puzzleId, 0),
            lastPlayedMillis = prefs.getLong(KEY_LAST_PLAYED + puzzleId, 0L)
        )
    }

    fun saveProgress(progress: PuzzleProgress) {
        prefs.edit().apply {
            putLong(KEY_BEST_TIME + progress.puzzleId, progress.bestTimeMillis)
            putInt(KEY_TIMES_COMPLETED + progress.puzzleId, progress.timesCompleted)
            putLong(KEY_LAST_PLAYED + progress.puzzleId, progress.lastPlayedMillis)
            apply()
        }
    }

    fun updateBestTime(puzzleId: String, timeMillis: Long): Boolean {
        val currentProgress = getProgress(puzzleId)
        val isNewRecord = timeMillis < currentProgress.bestTimeMillis

        val updatedProgress = currentProgress.copy(
            bestTimeMillis = if (isNewRecord) timeMillis else currentProgress.bestTimeMillis,
            timesCompleted = currentProgress.timesCompleted + 1,
            lastPlayedMillis = System.currentTimeMillis()
        )

        saveProgress(updatedProgress)
        return isNewRecord
    }
}
