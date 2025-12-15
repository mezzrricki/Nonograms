package com.ski.mezyn.nonograms.ui.game.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TimerDisplay(
    elapsedTimeMillis: Long,
    modifier: Modifier = Modifier
) {
    val seconds = (elapsedTimeMillis / 1000) % 60
    val minutes = (elapsedTimeMillis / 1000) / 60

    Text(
        text = String.format("%02d:%02d", minutes, seconds),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
    )
}
