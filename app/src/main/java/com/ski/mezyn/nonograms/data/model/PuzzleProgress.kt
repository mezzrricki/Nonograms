package com.ski.mezyn.nonograms.data.model

data class PuzzleProgress(
    val puzzleId: String,
    val bestTimeMillis: Long = Long.MAX_VALUE,
    val timesCompleted: Int = 0,
    val lastPlayedMillis: Long = 0L
)
