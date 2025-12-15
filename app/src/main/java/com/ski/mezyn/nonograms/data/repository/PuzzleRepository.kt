package com.ski.mezyn.nonograms.data.repository

import com.ski.mezyn.nonograms.data.model.Difficulty
import com.ski.mezyn.nonograms.data.model.Puzzle
import com.ski.mezyn.nonograms.util.NonogramSolver

object PuzzleRepository {
    private val puzzles: List<Puzzle> by lazy {
        listOf(
            // Easy 5x5 Puzzles
            createPuzzle(
                id = "heart",
                name = "Heart",
                difficulty = Difficulty.EASY,
                pattern = """
                    01010
                    11111
                    11111
                    01110
                    00100
                """
            ),
            createPuzzle(
                id = "arrow",
                name = "Arrow",
                difficulty = Difficulty.EASY,
                pattern = """
                    00100
                    01110
                    00100
                    00100
                    00100
                """
            ),
            createPuzzle(
                id = "cross",
                name = "Cross",
                difficulty = Difficulty.EASY,
                pattern = """
                    00100
                    00100
                    11111
                    00100
                    00100
                """
            ),
            createPuzzle(
                id = "diamond",
                name = "Diamond",
                difficulty = Difficulty.EASY,
                pattern = """
                    00100
                    01110
                    11111
                    01110
                    00100
                """
            ),

            // Medium 10x10 Puzzles
            createPuzzle(
                id = "house",
                name = "House",
                difficulty = Difficulty.MEDIUM,
                pattern = """
                    0001110000
                    0010001000
                    0100000100
                    1000000010
                    0100000100
                    0100000100
                    0111111100
                    0100110100
                    0100110100
                    0111111100
                """
            ),
            createPuzzle(
                id = "tree",
                name = "Tree",
                difficulty = Difficulty.MEDIUM,
                pattern = """
                    0000100000
                    0001110000
                    0011111000
                    0111111100
                    1111111110
                    0001110000
                    0001110000
                    0001110000
                    0011111000
                    0111111100
                """
            ),
            createPuzzle(
                id = "star",
                name = "Star",
                difficulty = Difficulty.MEDIUM,
                pattern = """
                    0000100000
                    0001110000
                    0001110000
                    1111111110
                    0111111100
                    0011111000
                    0011011000
                    0010001000
                    0110001100
                    1100000110
                """
            ),
            createPuzzle(
                id = "umbrella",
                name = "Umbrella",
                difficulty = Difficulty.MEDIUM,
                pattern = """
                    0111111100
                    1111111110
                    1111111110
                    0111111100
                    0001110000
                    0001110000
                    0001110000
                    0001110000
                    0001110000
                    0110001100
                """
            ),

            // Hard 15x15 Puzzles
            createPuzzle(
                id = "cat",
                name = "Cat",
                difficulty = Difficulty.HARD,
                pattern = """
                    010000000000100
                    011000000001100
                    001100000011000
                    000111111110000
                    000110000110000
                    000110000110000
                    001111111111000
                    001111111111000
                    001111001111000
                    001111001111000
                    000111001110000
                    000011111100000
                    000001110000000
                    000000100000000
                    000000100000000
                """
            ),
            createPuzzle(
                id = "flower",
                name = "Flower",
                difficulty = Difficulty.HARD,
                pattern = """
                    000001110000000
                    000011111000000
                    000111111100000
                    001111111110000
                    001111111110000
                    011111111111000
                    011111111111000
                    001111111110000
                    001111111110000
                    000111111100000
                    000011111000000
                    000001110000000
                    000000100000000
                    000000100000000
                    000001110000000
                """
            ),
            createPuzzle(
                id = "butterfly",
                name = "Butterfly",
                difficulty = Difficulty.HARD,
                pattern = """
                    000000100000000
                    000000100000000
                    011000100001100
                    111100100011110
                    111110100111110
                    111111101111110
                    011111111111100
                    001111111111000
                    000111111110000
                    000011111100000
                    000001111000000
                    000000100000000
                    000000100000000
                    000000100000000
                    000000100000000
                """
            ),
            createPuzzle(
                id = "rocket",
                name = "Rocket",
                difficulty = Difficulty.HARD,
                pattern = """
                    000000100000000
                    000001110000000
                    000011111000000
                    000111111100000
                    000111111100000
                    000011111000000
                    000011111000000
                    000011111000000
                    000011111000000
                    000011111000000
                    000011111000000
                    000111111100000
                    001111111110000
                    011000000011000
                    110000000001100
                """
            )
        )
    }

    fun getAllPuzzles(): List<Puzzle> = puzzles

    fun getPuzzlesByDifficulty(difficulty: Difficulty): List<Puzzle> {
        return puzzles.filter { it.difficulty == difficulty }
    }

    fun getPuzzleById(id: String): Puzzle? {
        return puzzles.find { it.id == id }
    }

    private fun createPuzzle(
        id: String,
        name: String,
        difficulty: Difficulty,
        pattern: String
    ): Puzzle {
        // Parse the pattern string into a 2D boolean grid
        val solution = pattern.trim().lines().map { line ->
            line.trim().map { char -> char == '1' }
        }

        val gridSize = solution.size

        // Generate clues using NonogramSolver
        val rowClues = NonogramSolver.generateRowClues(solution)
        val columnClues = NonogramSolver.generateColumnClues(solution)

        return Puzzle(
            id = id,
            name = name,
            difficulty = difficulty,
            gridSize = gridSize,
            solution = solution,
            rowClues = rowClues,
            columnClues = columnClues
        )
    }
}
