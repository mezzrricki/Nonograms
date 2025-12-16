package com.ski.mezyn.nonograms.ui.game.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ski.mezyn.nonograms.data.model.CellState
import com.ski.mezyn.nonograms.data.model.Puzzle
import com.ski.mezyn.nonograms.data.model.GameState
import kotlin.math.min

private enum class DragDirection {
    HORIZONTAL,
    VERTICAL
}

@Composable
fun NonogramGridWithClues(
    puzzle: Puzzle,
    gameState: GameState,
    onCellTap: (row: Int, col: Int) -> Unit,
    onDragStart: (row: Int, col: Int) -> CellState,
    onDragCell: (row: Int, col: Int, targetState: CellState) -> Unit,
    onDragEnd: () -> Unit,
    modifier: Modifier = Modifier
) {
    @Suppress("UnusedBoxWithConstraintsScope")  // False positive - we use maxWidth and maxHeight
    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val density = LocalDensity.current
        val maxWidthPx = with(density) { maxWidth.toPx() }
        val maxHeightPx = with(density) { maxHeight.toPx() }

        // Calculate cell size based on available space
        // Reserve space for clues (more for color puzzles)
        val clueSpaceWidth = if (puzzle.isColorPuzzle) 80.dp else 60.dp
        val clueSpaceHeight = if (puzzle.isColorPuzzle) 80.dp else 60.dp
        val clueSpaceWidthPx = with(density) { clueSpaceWidth.toPx() }
        val clueSpaceHeightPx = with(density) { clueSpaceHeight.toPx() }

        val availableWidth = maxWidthPx - clueSpaceWidthPx - with(density) { 32.dp.toPx() }
        val availableHeight = maxHeightPx - clueSpaceHeightPx - with(density) { 32.dp.toPx() }

        val cellSize = min(
            availableWidth / puzzle.gridSize,
            availableHeight / puzzle.gridSize
        )

        val cellSizeDp = with(density) { cellSize.toDp() }
        val gridSize = cellSizeDp * puzzle.gridSize

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
                    colorPalette = puzzle.colorPalette,
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
                    colorPalette = puzzle.colorPalette,
                    cellSize = cellSizeDp.value,
                    modifier = Modifier.width(clueSpaceWidth)
                )

                // Grid
                NonogramGrid(
                    puzzle = puzzle,
                    gridSize = puzzle.gridSize,
                    currentGrid = gameState.currentGrid,
                    cellSize = cellSize,
                    onCellTap = onCellTap,
                    onDragStart = onDragStart,
                    onDragCell = onDragCell,
                    onDragEnd = onDragEnd,
                    modifier = Modifier.size(gridSize)
                )
            }
        }
    }
}

@Composable
private fun NonogramGrid(
    puzzle: Puzzle,
    gridSize: Int,
    currentGrid: List<List<CellState>>,
    cellSize: Float,
    onCellTap: (row: Int, col: Int) -> Unit,
    onDragStart: (row: Int, col: Int) -> CellState,
    onDragCell: (row: Int, col: Int, targetState: CellState) -> Unit,
    onDragEnd: () -> Unit,
    modifier: Modifier = Modifier
) {
    var draggedCells by remember { mutableStateOf<Set<Pair<Int, Int>>>(emptySet()) }
    var isDragging by remember { mutableStateOf(false) }
    var dragTargetState by remember { mutableStateOf<CellState?>(null) }
    var dragStartCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var dragDirection by remember { mutableStateOf<DragDirection?>(null) }

    Canvas(
        modifier = modifier
            .background(Color.White)
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
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        val row = (offset.y / cellSize).toInt()
                        val col = (offset.x / cellSize).toInt()
                        if (row in 0 until gridSize && col in 0 until gridSize) {
                            dragStartCell = Pair(row, col)
                            draggedCells = setOf(Pair(row, col))
                            dragDirection = null
                            dragTargetState = onDragStart(row, col)
                            isDragging = true
                        }
                    },
                    onDrag = { change, _ ->
                        change.consume()

                        val startCell = dragStartCell ?: return@detectDragGestures

                        // Allow drag to continue even if temporarily outside bounds
                        val row = (change.position.y / cellSize).toInt()
                        val col = (change.position.x / cellSize).toInt()

                        // Determine drag direction on first move (even if outside bounds)
                        if (dragDirection == null && (row != startCell.first || col != startCell.second)) {
                            val rowDiff = kotlin.math.abs(row - startCell.first)
                            val colDiff = kotlin.math.abs(col - startCell.second)

                            dragDirection = if (rowDiff > colDiff) {
                                DragDirection.VERTICAL
                            } else {
                                DragDirection.HORIZONTAL
                            }
                        }

                        // Constrain to drag direction
                        val constrainedCell = when (dragDirection) {
                            DragDirection.HORIZONTAL -> Pair(startCell.first, col) // Lock row
                            DragDirection.VERTICAL -> Pair(row, startCell.second) // Lock column
                            null -> Pair(row, col) // No direction yet
                        }

                        // Clamp to grid bounds
                        val clampedRow = constrainedCell.first.coerceIn(0 until gridSize)
                        val clampedCol = constrainedCell.second.coerceIn(0 until gridSize)
                        val clampedCell = Pair(clampedRow, clampedCol)

                        // Apply if this is a new cell we haven't touched yet
                        if (clampedCell !in draggedCells) {
                            draggedCells = draggedCells + clampedCell
                            // Apply the same state determined by the first cell
                            dragTargetState?.let { targetState ->
                                onDragCell(clampedCell.first, clampedCell.second, targetState)
                            }
                        }
                    },
                    onDragEnd = {
                        isDragging = false
                        draggedCells = emptySet()
                        dragTargetState = null
                        dragStartCell = null
                        dragDirection = null
                        onDragEnd()
                    },
                    onDragCancel = {
                        isDragging = false
                        draggedCells = emptySet()
                        dragTargetState = null
                        dragStartCell = null
                        dragDirection = null
                        onDragEnd()
                    }
                )
            }
    ) {
        // Draw grid background with subtle alternating pattern
        for (row in 0 until gridSize) {
            for (col in 0 until gridSize) {
                val x = col * cellSize
                val y = row * cellSize

                // Alternating background for better visibility
                val bgColor = if ((row + col) % 2 == 0) {
                    Color(0xFFF5F5F5)
                } else {
                    Color(0xFFEEEEEE)
                }

                drawRect(
                    color = bgColor,
                    topLeft = Offset(x, y),
                    size = Size(cellSize, cellSize)
                )
            }
        }

        // Draw cell states on top of background
        currentGrid.forEachIndexed { row, rowCells ->
            rowCells.forEachIndexed { col, cellState ->
                val x = col * cellSize
                val y = row * cellSize

                when (cellState) {
                    is CellState.Filled -> {
                        // Draw filled cell with color from palette or black for B&W
                        val cellColor = if (puzzle.isColorPuzzle && puzzle.colorPalette != null) {
                            // Use color from palette (colorIndex is 1-based)
                            if (cellState.colorIndex > 0 && cellState.colorIndex <= puzzle.colorPalette.size) {
                                puzzle.colorPalette[cellState.colorIndex - 1]
                            } else {
                                Color.Black // Fallback
                            }
                        } else {
                            Color.Black // B&W puzzle
                        }
                        drawRect(
                            color = cellColor,
                            topLeft = Offset(x, y),
                            size = Size(cellSize, cellSize)
                        )
                    }
                    is CellState.Marked -> {
                        // Draw X mark
                        drawXMark(
                            topLeft = Offset(x, y),
                            cellSize = cellSize,
                            color = Color(0xFF757575)
                        )
                    }
                    is CellState.Error -> {
                        // Draw error cell (red)
                        drawRect(
                            color = Color(0xFFEF5350),
                            topLeft = Offset(x, y),
                            size = Size(cellSize, cellSize)
                        )
                        // Draw X mark on top
                        drawXMark(
                            topLeft = Offset(x, y),
                            cellSize = cellSize,
                            color = Color(0xFF8B0000)
                        )
                    }
                    is CellState.Empty -> {
                        // Background already drawn
                    }
                }
            }
        }

        // Draw grid lines with pixel-perfect alignment
        for (i in 0..gridSize) {
            // Round to nearest pixel to avoid anti-aliasing artifacts
            val offset = kotlin.math.round(i * cellSize)

            // More pronounced lines every 5 cells for better readability
            val (strokeWidth, lineColor) = when {
                i == 0 || i == gridSize -> Pair(4f, Color(0xFF000000)) // Outer border
                i % 5 == 0 -> Pair(3f, Color(0xFF424242)) // Thick lines every 5
                else -> Pair(1f, Color(0xFFBDBDBD)) // Regular lines (1px for consistency)
            }

            // Horizontal line
            drawLine(
                color = lineColor,
                start = Offset(0f, offset),
                end = Offset(gridSize * cellSize, offset),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Butt
            )

            // Vertical line
            drawLine(
                color = lineColor,
                start = Offset(offset, 0f),
                end = Offset(offset, gridSize * cellSize),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Butt
            )
        }

        // Highlight dragged cells
        if (isDragging) {
            draggedCells.forEach { (row, col) ->
                val x = col * cellSize
                val y = row * cellSize
                drawRect(
                    color = Color(0x3300BCD4), // Semi-transparent cyan highlight
                    topLeft = Offset(x, y),
                    size = Size(cellSize, cellSize)
                )
            }
        }
    }
}

private fun DrawScope.drawXMark(
    topLeft: Offset,
    cellSize: Float,
    color: Color
) {
    val padding = cellSize * 0.3f
    val startX = topLeft.x + padding
    val startY = topLeft.y + padding
    val endX = topLeft.x + cellSize - padding
    val endY = topLeft.y + cellSize - padding

    // Calculate stroke width based on cell size for better visibility
    val strokeWidth = (cellSize * 0.08f).coerceAtLeast(3f)

    // Draw X (two diagonal lines) with thicker strokes
    drawLine(
        color = color,
        start = Offset(startX, startY),
        end = Offset(endX, endY),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )

    drawLine(
        color = color,
        start = Offset(endX, startY),
        end = Offset(startX, endY),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}
