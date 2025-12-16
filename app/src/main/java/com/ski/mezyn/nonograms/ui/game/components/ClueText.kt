package com.ski.mezyn.nonograms.ui.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ski.mezyn.nonograms.data.model.ColorClue

@Composable
fun RowClues(
    clues: List<List<ColorClue>>,
    colorPalette: List<Color>?,
    cellSize: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        clues.forEach { rowClues ->
            Box(
                modifier = Modifier.height(cellSize.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    rowClues.forEach { clue ->
                        ClueNumber(
                            clue = clue,
                            colorPalette = colorPalette
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ColumnClues(
    clues: List<List<ColorClue>>,
    colorPalette: List<Color>?,
    cellSize: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        clues.forEach { columnClues ->
            Box(
                modifier = Modifier.width(cellSize.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                    modifier = Modifier.padding(bottom = 2.dp)
                ) {
                    columnClues.forEach { clue ->
                        ClueNumber(
                            clue = clue,
                            colorPalette = colorPalette
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ClueNumber(
    clue: ColorClue,
    colorPalette: List<Color>?,
    modifier: Modifier = Modifier
) {
    val isColorPuzzle = colorPalette != null && colorPalette.isNotEmpty()
    val clueColor = if (isColorPuzzle && clue.colorIndex > 0 && clue.colorIndex <= colorPalette.size) {
        colorPalette[clue.colorIndex - 1]
    } else {
        Color.Transparent
    }

    val textColor = if (isColorPuzzle) {
        // Use white text for dark colors, black for light colors
        if (isColorDark(clueColor)) Color.White else Color.Black
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = modifier
            .then(
                if (isColorPuzzle) {
                    Modifier
                        .background(clueColor, RoundedCornerShape(3.dp))
                        .border(0.5.dp, Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(3.dp))
                        .padding(horizontal = 3.dp, vertical = 1.dp)
                        .widthIn(min = 16.dp)
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = clue.count.toString(),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isColorPuzzle) FontWeight.Bold else FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = textColor
        )
    }
}

// Helper function to determine if a color is dark
private fun isColorDark(color: Color): Boolean {
    val luminance = (0.299 * color.red + 0.587 * color.green + 0.114 * color.blue)
    return luminance < 0.5f
}
