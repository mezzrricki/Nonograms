# CLAUDE.MD - Android Development Guide

## Project Overview
This is an Android Nonogram puzzle game built with Kotlin and Jetpack Compose. The app follows modern Android architecture patterns and best practices.

## Development Principles

### 1. Architecture Pattern: MVVM + Clean Architecture
- **UI Layer**: Jetpack Compose components
- **ViewModel Layer**: Business logic and state management
- **Repository Layer**: Data access abstraction
- **Data Layer**: Room database, DataStore, and data models

### 2. Core Android Technologies
- **UI Framework**: Jetpack Compose (declarative UI)
- **Language**: Kotlin (100% Kotlin codebase)
- **Dependency Injection**: Manual DI or Hilt/Koin if needed
- **Database**: Room for local storage
- **Navigation**: Compose Navigation
- **Async**: Coroutines and Flow for reactive streams

### 3. Code Organization
```
app/src/main/java/com/ski/mezyn/nonograms/
├── data/
│   ├── model/          # Data classes, entities
│   ├── repository/     # Repository implementations
│   └── local/          # Room database, DAOs
├── ui/
│   ├── theme/          # Material theme, colors, typography
│   ├── components/     # Reusable UI components
│   └── [feature]/      # Feature-specific screens and ViewModels
└── utils/              # Helper functions and utilities
```

### 4. Naming Conventions
- **Files**: PascalCase (e.g., `GameViewModel.kt`, `NonogramGrid.kt`)
- **Classes**: PascalCase
- **Functions**: camelCase
- **Variables**: camelCase
- **Constants**: UPPER_SNAKE_CASE
- **Composables**: PascalCase (e.g., `@Composable fun GameScreen()`)

### 5. Jetpack Compose Best Practices

#### State Management
- Use `remember` for simple state
- Use `rememberSaveable` for state that survives configuration changes
- Hoist state to ViewModels for complex business logic
- Use `StateFlow` in ViewModels, collect as state in Composables

#### Composition
```kotlin
// Good: Stateless composable
@Composable
fun NonogramCell(
    state: CellState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) { /* ... */ }

// Good: State hoisting pattern
@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val gameState by viewModel.gameState.collectAsState()
    GameContent(
        gameState = gameState,
        onCellClick = viewModel::onCellClick
    )
}
```

#### Performance
- Use `key()` for list items that can be reordered
- Avoid doing work in composition phase
- Use `derivedStateOf` for computed state
- Keep Composables small and focused
- Use `Modifier` parameters for customization

### 6. ViewModel Guidelines
- Expose UI state via `StateFlow` or `State`
- Handle user actions via public methods
- Keep business logic in ViewModels, not Composables
- Use `viewModelScope` for coroutines
- Never pass Context to ViewModels (use Application context if needed)

```kotlin
class GameViewModel : ViewModel() {
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    fun onCellClick(row: Int, col: Int) {
        viewModelScope.launch {
            // Handle cell click logic
        }
    }
}
```

### 7. Data Model Patterns

#### Sealed Classes for State
Use sealed classes for type-safe state representation:
```kotlin
sealed class CellState {
    object Empty : CellState()
    object Marked : CellState()
    data class Filled(val colorIndex: Int = 0) : CellState()
    data class Error(val colorIndex: Int = 0) : CellState()
}
```

#### Data Classes for Models
```kotlin
data class GameState(
    val grid: List<List<CellState>>,
    val isComplete: Boolean = false,
    val moves: Int = 0
)
```

### 8. Resource Management
- **Strings**: All user-facing text in `strings.xml`
- **Dimensions**: Use `dp` for sizes, `sp` for text
- **Colors**: Define in theme, not hardcoded
- **Images**: Use vector drawables when possible

### 9. Testing Strategy

#### Unit Tests
- Test ViewModels business logic
- Test Repository data transformations
- Test utility functions
- Use JUnit 4/5 and MockK for mocking

#### UI Tests
- Test Composables with `createComposeRule()`
- Test navigation flows
- Test user interactions
- Use semantics for testable UI

#### Test Structure
```kotlin
class GameViewModelTest {
    @Test
    fun `onCellClick updates cell state correctly`() {
        // Given
        // When
        // Then
    }
}
```

### 10. Error Handling
- Use sealed classes for Result types
- Handle exceptions in ViewModels
- Show user-friendly error messages
- Log errors for debugging

```kotlin
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
```

### 11. Performance Optimization
- Use `LazyColumn`/`LazyRow` for large lists
- Avoid unnecessary recompositions
- Use `remember` to cache expensive calculations
- Profile with Android Profiler
- Watch for memory leaks with LeakCanary

### 12. Git Workflow
- **Feature branches**: `feature/color-puzzles`
- **Commit messages**: Clear, descriptive (e.g., "Add color support to cell state")
- **Before commit**: Run tests, check for compilation errors
- **Keep commits atomic**: One logical change per commit

### 13. Code Quality
- Follow Kotlin coding conventions
- Use meaningful variable names
- Write self-documenting code (minimize comments)
- Keep functions small and focused
- Apply SOLID principles
- Use Kotlin idioms (extension functions, scope functions, etc.)

### 14. Dependencies Management
- Keep dependencies up to date
- Use version catalogs for dependency management
- Avoid unnecessary dependencies
- Review licenses for third-party libraries

### 15. Security Considerations
- Never hardcode API keys or secrets
- Use ProGuard/R8 for release builds
- Validate all user inputs
- Use HTTPS for network calls
- Store sensitive data in EncryptedSharedPreferences

## Development Workflow

### Starting New Features
1. Read and understand existing code
2. Identify affected files and components
3. Plan changes (data model → repository → ViewModel → UI)
4. Implement incrementally
5. Test each component
6. Update documentation if needed

### Code Review Checklist
- [ ] Code follows architecture patterns
- [ ] No hardcoded strings or values
- [ ] Proper error handling
- [ ] State management is correct
- [ ] Composables are reusable and testable
- [ ] No performance issues (excessive recomposition)
- [ ] Tests are written and passing

## Common Patterns in This Project

### Cell State Management
Cells use a sealed class to represent different states (Empty, Filled, Marked, Error) with color support.

### Grid Rendering
Uses `LazyVerticalGrid` for efficient rendering of the game board.

### Progress Tracking
Repository pattern for saving and loading game progress.

## Current Project State
See `PROGRESS.md` for the current work in progress and status of ongoing features.

## Resources
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Kotlin Style Guide](https://kotlinlang.org/docs/coding-conventions.html)
- [Android Architecture Guide](https://developer.android.com/topic/architecture)
- [Material Design 3](https://m3.material.io/)