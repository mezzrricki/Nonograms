package com.ski.mezyn.nonograms.util

object NonogramSolver {
    /**
     * Generate clues for a row or column based on the solution pattern.
     * Returns a list of consecutive filled cell counts.
     * Example: [true, true, false, true] -> [2, 1]
     */
    fun generateClues(line: List<Boolean>): List<Int> {
        val clues = mutableListOf<Int>()
        var count = 0

        for (cell in line) {
            if (cell) {
                count++
            } else if (count > 0) {
                clues.add(count)
                count = 0
            }
        }

        // Add remaining count if line ends with filled cells
        if (count > 0) {
            clues.add(count)
        }

        // Return [0] for empty lines (no filled cells)
        return if (clues.isEmpty()) listOf(0) else clues
    }

    /**
     * Generate row clues for an entire puzzle solution.
     */
    fun generateRowClues(solution: List<List<Boolean>>): List<List<Int>> {
        return solution.map { row -> generateClues(row) }
    }

    /**
     * Generate column clues for an entire puzzle solution.
     */
    fun generateColumnClues(solution: List<List<Boolean>>): List<List<Int>> {
        if (solution.isEmpty()) return emptyList()

        val numColumns = solution[0].size
        return (0 until numColumns).map { col ->
            val column = solution.map { it[col] }
            generateClues(column)
        }
    }
}
