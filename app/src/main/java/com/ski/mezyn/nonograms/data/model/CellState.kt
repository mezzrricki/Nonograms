package com.ski.mezyn.nonograms.data.model

enum class CellState {
    EMPTY,    // Not filled yet
    FILLED,   // Player marked as filled (black)
    MARKED,   // Player marked as definitely empty (X)
    ERROR     // Filled but incorrect (shown after check)
}
