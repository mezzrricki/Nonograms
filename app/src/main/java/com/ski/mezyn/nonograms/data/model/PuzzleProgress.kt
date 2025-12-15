package com.ski.mezyn.nonograms.data.model

data class PuzzleProgress(
    val puzzleId: String,
    val bestTimeMillis: Long = Long.MAX_VALUE,
    val timesCompleted: Int = 0,
    val lastPlayedMillis: Long = 0L,
    // Saved progress for incomplete puzzles
    val savedGrid: String? = null,  // Serialized grid state (e.g., "0,1,2,0,1,1...")
    val savedTimeMillis: Long = 0L,  // Elapsed time when saved
    val savedMoves: Int = 0,         // Move count when saved
    val savedErrors: Int = 0         // Error count when saved
) {
    val isInProgress: Boolean
        get() = savedGrid != null && timesCompleted == 0

    val isCompleted: Boolean
        get() = timesCompleted > 0
}
