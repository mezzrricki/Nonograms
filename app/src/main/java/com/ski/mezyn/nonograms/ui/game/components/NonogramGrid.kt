package com.ski.mezyn.nonograms.ui.game.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ski.mezyn.nonograms.data.model.CellState
import com.ski.mezyn.nonograms.data.model.Puzzle
import com.ski.mezyn.nonograms.data.model.GameState
import kotlin.math.min

@Composable
fun NonogramGridWithClues(
    puzzle: Puzzle,
    gameState: GameState,
    onCellTap: (row: Int, col: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val density = LocalDensity.current
        val maxWidthPx = with(density) { maxWidth.toPx() }
        val maxHeightPx = with(density) { maxHeight.toPx() }

        // Calculate cell size based on available space
        // Reserve space for clues (approximate)
        val clueSpaceWidth = 60.dp
        val clueSpaceHeight = 60.dp
        val clueSpaceWidthPx = with(density) { clueSpaceWidth.toPx() }
        val clueSpaceHeightPx = with(density) { clueSpaceHeight.toPx() }

        val availableWidth = maxWidthPx - clueSpaceWidthPx - with(density) { 32.dp.toPx() }
        val availableHeight = maxHeightPx - clueSpaceHeightPx - with(density) { 32.dp.toPx() }

        val cellSize = min(
            availableWidth / puzzle.gridSize,
            availableHeight / puzzle.gridSize
        )

        val cellSizeDp = with(density) { cellSize.toDp() }
        val gridSize = puzzle.gridSize * cellSizeDp

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Column clues at top
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.height(clueSpaceHeight)
            ) {
                Spacer(modifier = Modifier.width(clueSpaceWidth))
                ColumnClues(
                    clues = puzzle.columnClues,
                    cellSize = cellSizeDp.value
                )
            }

            // Row clues and grid
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                // Row clues on left
                RowClues(
                    clues = puzzle.rowClues,
                    cellSize = cellSizeDp.value,
                    modifier = Modifier.width(clueSpaceWidth)
                )

                // Grid
                NonogramGrid(
                    gridSize = puzzle.gridSize,
                    currentGrid = gameState.currentGrid,
                    cellSize = cellSize,
                    onCellTap = onCellTap,
                    modifier = Modifier.size(gridSize)
                )
            }
        }
    }
}

@Composable
private fun NonogramGrid(
    gridSize: Int,
    currentGrid: List<List<CellState>>,
    cellSize: Float,
    onCellTap: (row: Int, col: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        val row = (offset.y / cellSize).toInt()
                        val col = (offset.x / cellSize).toInt()
                        if (row in 0 until gridSize && col in 0 until gridSize) {
                            onCellTap(row, col)
                        }
                    }
                )
            }
    ) {
        // Draw cells
        currentGrid.forEachIndexed { row, rowCells ->
            rowCells.forEachIndexed { col, cellState ->
                val x = col * cellSize
                val y = row * cellSize

                when (cellState) {
                    CellState.FILLED -> {
                        // Draw filled cell (black)
                        drawRect(
                            color = Color.Black,
                            topLeft = Offset(x, y),
                            size = Size(cellSize, cellSize)
                        )
                    }
                    CellState.MARKED -> {
                        // Draw X mark
                        drawXMark(
                            topLeft = Offset(x, y),
                            cellSize = cellSize,
                            color = Color.Gray
                        )
                    }
                    CellState.ERROR -> {
                        // Draw error cell (red)
                        drawRect(
                            color = Color(0xFFEF5350),
                            topLeft = Offset(x, y),
                            size = Size(cellSize, cellSize)
                        )
                    }
                    CellState.EMPTY -> {
                        // Nothing to draw for empty cells
                    }
                }
            }
        }

        // Draw grid lines
        for (i in 0..gridSize) {
            val offset = i * cellSize

            // Thicker lines every 5 cells for better readability
            val strokeWidth = if (i % 5 == 0) 3f else 1f
            val lineColor = if (i % 5 == 0) Color(0xFF424242) else Color(0xFF9E9E9E)

            // Horizontal line
            drawLine(
                color = lineColor,
                start = Offset(0f, offset),
                end = Offset(gridSize * cellSize, offset),
                strokeWidth = strokeWidth
            )

            // Vertical line
            drawLine(
                color = lineColor,
                start = Offset(offset, 0f),
                end = Offset(offset, gridSize * cellSize),
                strokeWidth = strokeWidth
            )
        }
    }
}

private fun DrawScope.drawXMark(
    topLeft: Offset,
    cellSize: Float,
    color: Color
) {
    val padding = cellSize * 0.25f
    val startX = topLeft.x + padding
    val startY = topLeft.y + padding
    val endX = topLeft.x + cellSize - padding
    val endY = topLeft.y + cellSize - padding

    // Draw X (two diagonal lines)
    drawLine(
        color = color,
        start = Offset(startX, startY),
        end = Offset(endX, endY),
        strokeWidth = 2f,
        cap = StrokeCap.Round
    )

    drawLine(
        color = color,
        start = Offset(endX, startY),
        end = Offset(startX, endY),
        strokeWidth = 2f,
        cap = StrokeCap.Round
    )
}
