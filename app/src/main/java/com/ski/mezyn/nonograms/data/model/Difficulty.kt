package com.ski.mezyn.nonograms.data.model

enum class Difficulty(val displayName: String, val gridSize: Int) {
    TINY("Tiny", 5),
    SMALL("Small", 8),
    MEDIUM("Medium", 10),
    LARGE("Large", 15),
    XLARGE("X-Large", 20),
    HUGE("Huge", 25),
    MASSIVE("Massive", 30)
}
