package com.ski.mezyn.nonograms.data.model

import androidx.compose.ui.graphics.Color

data class Puzzle(
    val id: String,
    val name: String,
    val difficulty: Difficulty,
    val category: Category,
    val gridSize: Int,
    val solution: List<List<Int>>, // 0 = empty, 1 = black (B&W), 1+ = color palette index
    val colorPalette: List<Color>? = null, // null for B&W puzzles, colors for color puzzles
    val rowClues: List<List<ColorClue>>, // Clues with color information
    val columnClues: List<List<ColorClue>> // Clues with color information
) {
    // Helper to check if this is a color puzzle
    val isColorPuzzle: Boolean get() = colorPalette != null && colorPalette.isNotEmpty()
}

data class ColorClue(
    val count: Int,
    val colorIndex: Int = 1  // 1 = black for B&W, 1+ = color palette index for color puzzles
)
