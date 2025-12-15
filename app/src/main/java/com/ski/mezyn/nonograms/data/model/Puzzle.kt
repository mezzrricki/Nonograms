package com.ski.mezyn.nonograms.data.model

data class Puzzle(
    val id: String,
    val name: String,
    val difficulty: Difficulty,
    val category: Category,
    val gridSize: Int,
    val solution: List<List<Boolean>>, // true = filled, false = empty
    val rowClues: List<List<Int>>, // Generated from solution
    val columnClues: List<List<Int>> // Generated from solution
)
