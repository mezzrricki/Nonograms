package com.ski.mezyn.nonograms.ui.game.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ski.mezyn.nonograms.ui.game.InputMode

@Composable
fun GameControls(
    inputMode: InputMode,
    canUndo: Boolean,
    canRedo: Boolean,
    onInputModeChange: (InputMode) -> Unit,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onCheck: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Input Mode Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = inputMode == InputMode.FILL,
                onClick = { onInputModeChange(InputMode.FILL) },
                label = { Text("Fill") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.CheckBox,
                        contentDescription = null
                    )
                },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = inputMode == InputMode.MARK,
                onClick = { onInputModeChange(InputMode.MARK) },
                label = { Text("Mark") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }

        // Action Buttons Row 1: Undo/Redo
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onUndo,
                enabled = canUndo,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Undo,
                    contentDescription = "Undo",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Undo")
            }

            OutlinedButton(
                onClick = onRedo,
                enabled = canRedo,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Redo,
                    contentDescription = "Redo",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Redo")
            }
        }

        // Action Buttons Row 2: Check/Clear
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onCheck,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Check",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Check")
            }

            OutlinedButton(
                onClick = onClear,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Clear",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Clear")
            }
        }
    }
}
