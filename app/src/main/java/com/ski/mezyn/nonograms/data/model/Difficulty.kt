package com.ski.mezyn.nonograms.data.model

enum class Difficulty(val displayName: String, val gridSize: Int) {
    EASY("Easy", 5),
    MEDIUM("Medium", 10),
    HARD("Hard", 15)
}
