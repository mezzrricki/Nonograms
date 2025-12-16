package com.ski.mezyn.nonograms.ui.game.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ski.mezyn.nonograms.data.model.ColorClue

@Composable
fun RowClues(
    clues: List<List<ColorClue>>,
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
                Text(
                    text = rowClues.joinToString(" ") { it.count.toString() },
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}

@Composable
fun ColumnClues(
    clues: List<List<ColorClue>>,
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
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    columnClues.forEach { clue ->
                        Text(
                            text = clue.count.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
