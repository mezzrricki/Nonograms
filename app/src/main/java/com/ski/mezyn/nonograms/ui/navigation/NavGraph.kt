package com.ski.mezyn.nonograms.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ski.mezyn.nonograms.ui.game.GameScreen
import com.ski.mezyn.nonograms.ui.puzzle_list.PuzzleListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.PuzzleList.route
    ) {
        composable(Screen.PuzzleList.route) {
            PuzzleListScreen(
                onPuzzleClick = { puzzleId ->
                    navController.navigate(Screen.Game.createRoute(puzzleId))
                }
            )
        }

        composable(
            route = Screen.Game.route,
            arguments = listOf(
                navArgument("puzzleId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getString("puzzleId") ?: return@composable
            GameScreen(
                puzzleId = puzzleId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
