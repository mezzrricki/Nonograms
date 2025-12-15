package com.ski.mezyn.nonograms.ui.game.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ski.mezyn.nonograms.data.model.Difficulty

@Composable
fun GameHeader(
    puzzleName: String,
    difficulty: Difficulty,
    mistakes: Int,
    elapsedTimeMillis: Long,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Puzzle info
        Column {
            Text(
                text = puzzleName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AssistChip(
                    onClick = { },
                    label = { Text(difficulty.displayName) }
                )
                if (mistakes > 0) {
                    AssistChip(
                        onClick = { },
                        label = { Text("Mistakes: $mistakes") },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            labelColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                    )
                }
            }
        }

        // Timer
        TimerDisplay(elapsedTimeMillis = elapsedTimeMillis)
    }
}
