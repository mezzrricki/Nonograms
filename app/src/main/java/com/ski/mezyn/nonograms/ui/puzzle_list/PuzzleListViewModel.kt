package com.ski.mezyn.nonograms.ui.puzzle_list

import androidx.lifecycle.ViewModel
import com.ski.mezyn.nonograms.data.model.Category
import com.ski.mezyn.nonograms.data.model.Difficulty
import com.ski.mezyn.nonograms.data.model.Puzzle
import com.ski.mezyn.nonograms.data.model.PuzzleProgress
import com.ski.mezyn.nonograms.data.repository.ProgressRepository
import com.ski.mezyn.nonograms.data.repository.PuzzleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PuzzleListState(
    val puzzlesByCategory: Map<Category, Map<Difficulty, List<Puzzle>>> = emptyMap(),
    val expandedCategories: Set<String> = emptySet(),
    val expandedDifficulties: Set<String> = emptySet(), // "categoryId:difficulty"
    val progressMap: Map<String, PuzzleProgress> = emptyMap() // puzzleId -> progress
)

class PuzzleListViewModel : ViewModel() {
    private val _state = MutableStateFlow(PuzzleListState())
    val state: StateFlow<PuzzleListState> = _state.asStateFlow()

    init {
        loadPuzzles()
    }

    fun refreshPuzzles() {
        loadPuzzles()
    }

    private fun loadPuzzles() {
        val puzzles = PuzzleRepository.getAllPuzzles()

        // Load progress for all puzzles
        val progressMap = puzzles.associate { puzzle ->
            puzzle.id to ProgressRepository.getProgress(puzzle.id)
        }

        // Group by category first, then by difficulty, then sort within difficulty
        val groupedByCategory = puzzles.groupBy { it.category }
            .mapValues { (_, categoryPuzzles) ->
                categoryPuzzles.groupBy { it.difficulty }
                    .mapValues { (_, difficultyPuzzles) ->
                        // Sort: incomplete first, then completed by best time
                        difficultyPuzzles.sortedWith(compareBy(
                            { puzzle ->
                                val progress = progressMap[puzzle.id]
                                // Incomplete puzzles (no completions) come first
                                if (progress == null || progress.timesCompleted == 0) 0 else 1
                            },
                            { puzzle ->
                                val progress = progressMap[puzzle.id]
                                // Among completed puzzles, sort by best time (fastest first)
                                progress?.bestTimeMillis ?: Long.MAX_VALUE
                            }
                        ))
                    }
            }

        _state.value = _state.value.copy(
            puzzlesByCategory = groupedByCategory,
            progressMap = progressMap
        )
    }

    fun toggleCategory(categoryId: String) {
        val currentExpanded = _state.value.expandedCategories
        val newExpanded = if (categoryId in currentExpanded) {
            currentExpanded - categoryId
        } else {
            currentExpanded + categoryId
        }
        _state.value = _state.value.copy(expandedCategories = newExpanded)
    }

    fun toggleDifficulty(categoryId: String, difficulty: Difficulty) {
        val key = "$categoryId:${difficulty.name}"
        val currentExpanded = _state.value.expandedDifficulties
        val newExpanded = if (key in currentExpanded) {
            currentExpanded - key
        } else {
            currentExpanded + key
        }
        _state.value = _state.value.copy(expandedDifficulties = newExpanded)
    }
}
