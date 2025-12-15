package com.ski.mezyn.nonograms.ui.navigation

sealed class Screen(val route: String) {
    data object PuzzleList : Screen("puzzle_list")
    data object Game : Screen("game/{puzzleId}") {
        fun createRoute(puzzleId: String) = "game/$puzzleId"
    }
}
