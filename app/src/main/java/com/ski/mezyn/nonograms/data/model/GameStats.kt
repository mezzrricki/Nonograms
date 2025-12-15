package com.ski.mezyn.nonograms.data.model

data class GameStats(
    val timeSeconds: Long,
    val mistakes: Int,
    val hintsUsed: Int = 0
)
