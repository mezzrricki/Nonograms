package com.ski.mezyn.nonograms.data.model

sealed class CellState {
    object Empty : CellState()
    object Marked : CellState()
    data class Filled(val colorIndex: Int = 0) : CellState() // 0 = black, 1+ = color palette index
    data class Error(val colorIndex: Int = 0) : CellState()
}
