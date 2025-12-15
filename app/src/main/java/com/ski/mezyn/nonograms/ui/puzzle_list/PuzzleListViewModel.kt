package com.ski.mezyn.nonograms.ui.puzzle_list

import androidx.lifecycle.ViewModel
import com.ski.mezyn.nonograms.data.model.Difficulty
import com.ski.mezyn.nonograms.data.model.Puzzle
import com.ski.mezyn.nonograms.data.repository.PuzzleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PuzzleListState(
    val puzzlesByDifficulty: Map<Difficulty, List<Puzzle>> = emptyMap()
)

class PuzzleListViewModel : ViewModel() {
    private val _state = MutableStateFlow(PuzzleListState())
    val state: StateFlow<PuzzleListState> = _state.asStateFlow()

    init {
        loadPuzzles()
    }

    private fun loadPuzzles() {
        val puzzles = PuzzleRepository.getAllPuzzles()
        val groupedPuzzles = puzzles.groupBy { it.difficulty }
        _state.value = PuzzleListState(puzzlesByDifficulty = groupedPuzzles)
    }
}
