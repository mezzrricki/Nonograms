package com.ski.mezyn.nonograms.ui.theme

import androidx.compose.ui.graphics.Color

// Light Theme - Sophisticated Blue-Indigo Palette
val Primary40 = Color(0xFF3D5AFE)        // Rich indigo blue
val OnPrimary40 = Color(0xFFFFFFFF)
val PrimaryContainer40 = Color(0xFFE8EAFE)  // Very light blue tint
val OnPrimaryContainer40 = Color(0xFF001A41)

val Secondary40 = Color(0xFF5E6A71)      // Sophisticated gray-blue
val OnSecondary40 = Color(0xFFFFFFFF)
val SecondaryContainer40 = Color(0xFFE2E8ED)
val OnSecondaryContainer40 = Color(0xFF1A1C1E)

val Tertiary40 = Color(0xFF7B5800)       // Warm gold accent
val OnTertiary40 = Color(0xFFFFFFFF)
val TertiaryContainer40 = Color(0xFFFFDDB3)
val OnTertiaryContainer40 = Color(0xFF271900)

// Surface colors with better contrast
val Surface40 = Color(0xFFFBFCFE)
val OnSurface40 = Color(0xFF1A1C1E)
val SurfaceVariant40 = Color(0xFFE1E2EC)
val OnSurfaceVariant40 = Color(0xFF44474F)

val Error40 = Color(0xFFB3261E)
val OnError40 = Color(0xFFFFFFFF)
val ErrorContainer40 = Color(0xFFF9DEDC)
val OnErrorContainer40 = Color(0xFF410E0B)

val Outline40 = Color(0xFF74777F)
val OutlineVariant40 = Color(0xFFC4C6D0)

// Dark Theme - Deep, Rich Colors
val Primary80 = Color(0xFFB4C5FF)
val OnPrimary80 = Color(0xFF002C71)
val PrimaryContainer80 = Color(0xFF1B3C8A)
val OnPrimaryContainer80 = Color(0xFFDAE2FF)

val Secondary80 = Color(0xFFC6CCD1)
val OnSecondary80 = Color(0xFF2F3236)
val SecondaryContainer80 = Color(0xFF45494E)
val OnSecondaryContainer80 = Color(0xFFE2E8ED)

val Tertiary80 = Color(0xFFFFB951)
val OnTertiary80 = Color(0xFF422C00)
val TertiaryContainer80 = Color(0xFF5E4100)
val OnTertiaryContainer80 = Color(0xFFFFDDB3)

// Dark surfaces
val Surface80 = Color(0xFF1A1C1E)
val OnSurface80 = Color(0xFFE3E2E6)
val SurfaceVariant80 = Color(0xFF44474F)
val OnSurfaceVariant80 = Color(0xFFC4C6D0)

val Error80 = Color(0xFFF2B8B5)
val OnError80 = Color(0xFF601410)
val ErrorContainer80 = Color(0xFF8C1D18)
val OnErrorContainer80 = Color(0xFFF9DEDC)

val Outline80 = Color(0xFF8E9099)
val OutlineVariant80 = Color(0xFF44474F)

// Game-Specific Colors (for both themes)
object GameColors {
    // Grid cell states
    val CellFilled = Color(0xFF1A1C1E)           // Deep charcoal for filled cells
    val CellMarked = Color(0xFF607D8B)           // Blue-gray for X marks
    val CellError = Color(0xFFEF5350)            // Vibrant red for errors
    val CellEmpty = Color(0xFFFFFFFF)            // Pure white

    // Grid backgrounds (alternating pattern)
    val GridBackground1Light = Color(0xFFF8F9FA)  // Subtle gray
    val GridBackground2Light = Color(0xFFECEFF1)  // Slightly darker gray
    val GridBackground1Dark = Color(0xFF262830)
    val GridBackground2Dark = Color(0xFF2E3139)

    // Grid lines
    val GridLineLight = Color(0xFFCFD8DC)         // Light gray
    val GridLineThickLight = Color(0xFF78909C)    // Medium gray for 5-cell lines
    val GridBorderLight = Color(0xFF455A64)       // Dark gray for borders

    val GridLineDark = Color(0xFF455A64)
    val GridLineThickDark = Color(0xFF90A4AE)
    val GridBorderDark = Color(0xFFB0BEC5)

    // Clue text
    val ClueTextLight = Color(0xFF37474F)
    val ClueTextDark = Color(0xFFCFD8DC)
    val ClueTextCompletedLight = Color(0xFF9E9E9E)  // Dimmed when row/column complete
    val ClueTextCompletedDark = Color(0xFF616161)

    // Success/completion
    val Success = Color(0xFF4CAF50)
    val SuccessContainer = Color(0xFFE8F5E9)
    val OnSuccess = Color(0xFFFFFFFF)

    // Drag highlight
    val DragHighlightLight = Color(0x4D3D5AFE)    // 30% opacity primary
    val DragHighlightDark = Color(0x4DB4C5FF)
}
