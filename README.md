# Nonograms Puzzle Game

A modern Android tablet application for playing nonogram puzzles (also known as picross or griddlers). Built with Jetpack Compose and Material Design 3, featuring 200+ handcrafted puzzles across 11 categories.

## Table of Contents
- [What are Nonograms?](#what-are-nonograms)
- [Features](#features)
- [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [How It Works](#how-it-works)
- [Adding New Puzzles](#adding-new-puzzles)
- [Game Mechanics](#game-mechanics)
- [Future Roadmap](#future-roadmap)
- [Contributing](#contributing)

## What are Nonograms?

Nonograms are logic puzzles where you fill in cells on a grid to reveal a hidden picture. Each row and column has numbers (called "clues") that tell you how many consecutive cells should be filled in that line.

**Example:**
```
Clues: [2, 1]
Grid:  [ ][ ][ ][ ][ ]

Solution: [■][■][ ][■][ ]
          (2 filled, space, 1 filled)
```

The goal is to use logic to determine which cells should be filled based on the row and column clues, eventually revealing a pixel art image!

## Features

### Current Features
- ✅ **200+ Puzzles** across 11 diverse categories
- ✅ **Multiple Difficulty Levels**: TINY (5×5), SMALL (8×8), MEDIUM (10×10), LARGE (15×15), XLARGE (20×20)
- ✅ **Touch & Drag Controls**: Optimized for tablets with directional lock
- ✅ **Auto-Marking**: Automatically marks completed rows/columns
- ✅ **Undo/Redo**: Full history management (50 moves)
- ✅ **Timer & Best Times**: Track your fastest completion for each puzzle
- ✅ **Progress Tracking**: See which puzzles you've completed
- ✅ **Dual Input Modes**: Switch between Fill and Mark modes
- ✅ **Modern UI**: Material Design 3 with dark theme support
- ✅ **Collapsible Categories**: Organize puzzles by category and difficulty
- ✅ **Completion Indicators**: Visual feedback for solved puzzles

### Puzzle Categories
1. **Abstract** (8 puzzles) - Geometric patterns and shapes
2. **Animals** (12 puzzles) - Cats, dogs, butterflies, elephants, and more
3. **Nature** (12 puzzles) - Trees, flowers, mountains, rainbows
4. **Objects** (12 puzzles) - Everyday items like keys, books, cameras
5. **Gaming** (80 puzzles) - Classic video game pixel art (Mario, Zelda, Pokémon, etc.)
6. **Symbols** (12 puzzles) - Stars, peace signs, anchors, crowns
7. **Food** (12 puzzles) - Fruits, desserts, beverages
8. **Transportation** (12 puzzles) - Cars, planes, rockets, ships
9. **Music & Arts** (12 puzzles) - Instruments, paintbrushes, musical notes
10. **Sports** (12 puzzles) - Balls, equipment, trophies
11. **Holidays** (15 puzzles) - Seasonal and festive themes

## Screenshots

*[TODO: Add screenshots of puzzle list, gameplay, and completion dialog]*

## Tech Stack

### Core Technologies
- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern declarative UI framework
- **Material Design 3** - Design system and theming
- **Coroutines & Flow** - Asynchronous programming and reactive state

### Architecture
- **MVVM Pattern** - Model-View-ViewModel architecture
- **StateFlow** - Reactive state management
- **ViewModel** - Lifecycle-aware UI state holder
- **Repository Pattern** - Data access abstraction
- **SharedPreferences** - Persistent storage for progress

### Key Libraries
- `androidx.compose.material3` - Material Design 3 components
- `androidx.navigation:navigation-compose` - Navigation between screens
- `androidx.lifecycle:lifecycle-viewmodel-compose` - ViewModel integration
- `androidx.compose.material:material-icons-extended` - Extended icon set

## Project Structure

```
app/src/main/java/com/ski/mezyn/nonograms/
│
├── data/                          # Data layer
│   ├── model/                     # Data models
│   │   ├── Category.kt            # Puzzle category definitions
│   │   ├── Difficulty.kt          # Difficulty levels (TINY to XLARGE)
│   │   ├── Puzzle.kt              # Puzzle data structure
│   │   ├── GameState.kt           # Current game state (grid, timer, etc.)
│   │   ├── GameStats.kt           # Move count and error tracking
│   │   ├── CellState.kt           # Individual cell states (EMPTY, FILLED, MARKED, ERROR)
│   │   └── PuzzleProgress.kt      # Completion and best time tracking
│   │
│   └── repository/                # Data access layer
│       ├── PuzzleRepository.kt    # Puzzle data source (hardcoded puzzles)
│       └── ProgressRepository.kt  # SharedPreferences for progress
│
├── ui/                            # UI layer
│   ├── theme/                     # Design system
│   │   ├── Color.kt               # Color palette (light/dark themes)
│   │   ├── Type.kt                # Typography scale
│   │   ├── Dimensions.kt          # Spacing, elevation, corner radius
│   │   └── Theme.kt               # Theme composition
│   │
│   ├── puzzle_list/               # Puzzle selection screen
│   │   ├── PuzzleListScreen.kt    # UI for browsing puzzles
│   │   └── PuzzleListViewModel.kt # State management for puzzle list
│   │
│   └── game/                      # Gameplay screen
│       ├── GameScreen.kt          # Main game UI
│       ├── GameViewModel.kt       # Game state and logic
│       └── components/            # Reusable game components
│           ├── NonogramGrid.kt    # Canvas-based grid rendering
│           └── ControlsPanel.kt   # Input mode toggle, undo/redo
│
├── util/                          # Utility classes
│   └── NonogramSolver.kt          # Clue generation from puzzle grids
│
└── MainActivity.kt                # App entry point and navigation
```

## Getting Started

### Prerequisites
- **Android Studio**: Hedgehog (2023.1.1) or later
- **JDK**: 11 or higher
- **Android SDK**: API 36 (Android 13+)
- **Gradle**: 8.0+

### Building the Project

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd nonograms
   ```

2. **Open in Android Studio**
   - File → Open → Select the `nonograms` directory
   - Wait for Gradle sync to complete

3. **Run on emulator or device**
   - Create an Android Virtual Device (AVD) with API 36+
   - Recommended: Tablet form factor (10" or larger)
   - Click "Run" (▶️) button or press `Shift+F10`

4. **Build APK**
   ```bash
   ./gradlew assembleDebug    # Debug build
   ./gradlew assembleRelease  # Release build (requires signing)
   ```

### Configuration

**Gradle Configuration** (`app/build.gradle.kts`):
```kotlin
android {
    namespace = "com.ski.mezyn.nonograms"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ski.mezyn.nonograms"
        minSdk = 36
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
}
```

## How It Works

### Data Flow

```
┌─────────────────┐
│ PuzzleRepository│ ← Hardcoded puzzle data (List<Puzzle>)
└────────┬────────┘
         │
         ↓
┌────────────────────┐
│PuzzleListViewModel │ ← Groups puzzles by category/difficulty
└────────┬───────────┘
         │
         ↓
┌──────────────────┐
│ PuzzleListScreen │ ← Displays collapsible categories
└────────┬─────────┘
         │ User selects puzzle
         ↓
┌──────────────┐
│ GameViewModel│ ← Loads puzzle, manages game state
└──────┬───────┘
       │
       ↓
┌─────────────┐
│ GameScreen  │ ← Renders grid, handles input
└─────────────┘
```

### Core Concepts

#### 1. Puzzle Data Structure

```kotlin
data class Puzzle(
    val id: String,              // Unique identifier ("mario_coin")
    val name: String,            // Display name ("Coin")
    val difficulty: Difficulty,  // TINY, SMALL, MEDIUM, LARGE, XLARGE
    val category: Category,      // Category object
    val gridSize: Int,           // 5, 8, 10, 15, or 20
    val solution: List<List<Boolean>>, // 2D grid: true = filled, false = empty
    val rowClues: List<List<Int>>,     // Clues for each row
    val columnClues: List<List<Int>>   // Clues for each column
)
```

**Example Puzzle Creation**:
```kotlin
createPuzzle("heart", "Heart", Difficulty.TINY, Category.ABSTRACT, """
    01010
    11111
    11111
    01110
    00100
""")
```
- `0` = empty cell
- `1` = filled cell
- The string is parsed into a 2D Boolean grid
- Clues are automatically generated by `NonogramSolver`

#### 2. Cell State Management

```kotlin
enum class CellState {
    EMPTY,    // Blank cell
    FILLED,   // User marked as filled (black)
    MARKED,   // User marked with X (won't fill)
    ERROR     // Incorrect fill (shown in red)
}
```

#### 3. Game State

```kotlin
data class GameState(
    val puzzleId: String,
    val grid: List<List<CellState>>,  // Current cell states
    val stats: GameStats,              // Moves, errors
    val isComplete: Boolean,
    val completedRows: Set<Int>,       // Auto-mark tracking
    val completedColumns: Set<Int>
)
```

#### 4. Auto-Marking Logic

When a row or column's filled cells exactly match its clues, all empty cells in that line are automatically marked with X:

```kotlin
// Example: Row clue is [3]
// Grid: [■][■][■][ ][ ]
// After move: [■][■][■][X][X] ← Auto-marked
```

This is implemented in `GameViewModel.autoMarkCompletedLines()`.

#### 5. Canvas-Based Grid Rendering

The grid is drawn using Compose Canvas for performance:

```kotlin
Canvas(modifier = Modifier.fillMaxSize()) {
    // Draw grid background (alternating 5×5 blocks)
    // Draw cell states (filled = black, marked = X, error = red)
    // Draw grid lines (thick lines every 5 cells)
    // Draw row/column clues
}
```

#### 6. Gesture Detection

```kotlin
Modifier.pointerInput(Unit) {
    detectDragGestures(
        onDragStart = { /* Lock to horizontal or vertical */ },
        onDrag = { /* Fill/mark cells in direction */ }
    )
}
```

Directional locking ensures smooth tablet gameplay.

## Adding New Puzzles

### Step 1: Create Puzzle Art

Design your pixel art as a grid of 0s and 1s:

```kotlin
"""
00111100
01111110
11111111
11111111
11111111
01111110
00111100
00011000
"""
```

**Tips:**
- Use a text editor with monospace font
- Start simple (8×8) before attempting larger sizes
- Test that the pattern is solvable (unique solution)
- Remember: 0 = empty, 1 = filled

### Step 2: Add to Repository

Open `PuzzleRepository.kt` and add your puzzle to the appropriate category method:

```kotlin
private fun generateFoodPuzzles(): List<Puzzle> {
    return listOf(
        // Existing puzzles...

        createPuzzle("food_banana", "Banana", Difficulty.SMALL, Category.FOOD, """
            00111000
            01111100
            11111110
            11111110
            01111100
            00111000
            00011000
            00001000
        """)
    )
}
```

### Step 3: Verify Grid Size Matches Difficulty

| Difficulty | Grid Size | Use Case |
|------------|-----------|----------|
| TINY       | 5×5       | Very simple shapes |
| SMALL      | 8×8       | Basic icons |
| MEDIUM     | 10×10     | Detailed objects |
| LARGE      | 15×15     | Complex scenes |
| XLARGE     | 20×20     | Intricate artwork |

### Step 4: Build and Test

```bash
./gradlew assembleDebug
```

Run the app and verify:
- ✅ Puzzle appears in correct category
- ✅ Clues are generated correctly
- ✅ Puzzle is solvable
- ✅ Completion is detected properly

### Creating a New Category

1. **Add category to `Category.kt`**:
```kotlin
val SPACE = Category("space", "Space & Astronomy", "Celestial objects and spacecraft")
```

2. **Create generation method in `PuzzleRepository.kt`**:
```kotlin
private fun generateSpacePuzzles(): List<Puzzle> {
    return listOf(
        createPuzzle("space_planet", "Planet", Difficulty.SMALL, Category.SPACE, """
            01111110
            11111111
            11111111
            11111111
            11111111
            11111111
            11111111
            01111110
        """)
    )
}
```

3. **Add to puzzles list**:
```kotlin
private val puzzles: List<Puzzle> by lazy {
    listOf(
        // ... existing categories
        *generateSpacePuzzles().toTypedArray()
    )
}
```

## Game Mechanics

### Input Modes

**Fill Mode** (default):
- Tap or drag to fill cells with black
- Tap filled cell to clear it

**Mark Mode** (toggle with X button):
- Tap or drag to mark cells with X
- Used to indicate cells that should stay empty
- Tap marked cell to clear it

### Validation

The game validates your solution in real-time:
- ✅ **Correct cells**: Remain black
- ❌ **Incorrect cells**: Turn red when you attempt to complete
- 🎉 **Completion**: Automatic detection + dialog with time

### Progress Saving

Progress is saved automatically in SharedPreferences:

```kotlin
data class PuzzleProgress(
    val puzzleId: String,
    val timesCompleted: Int,      // How many times solved
    val bestTimeMillis: Long      // Fastest completion time
)
```

**Storage location**: `app_preferences.xml`
**Cleared on**: App uninstall

### Scoring

Currently tracks:
- **Completion time**: Milliseconds to solve
- **Move count**: Number of cell state changes
- **Error count**: Incorrect fills (tracked but not penalized)

Best times are displayed on puzzle cards in the selection screen.

## Future Roadmap

### Phase 2-5: Color Puzzle Support (Planned)

The architecture is designed to support color puzzles:

#### New Data Models
```kotlin
data class ColorPuzzle(
    val colorPalette: List<Color>,        // Available colors
    val solution: List<List<Int>>,        // 0 = empty, 1+ = color index
    val rowClues: List<List<ColorClue>>,
    val columnClues: List<List<ColorClue>>
)

data class ColorClue(
    val count: Int,       // Number of consecutive cells
    val colorIndex: Int   // Which color from palette
)
```

#### Sealed CellState (Future)
```kotlin
sealed class CellState {
    object Empty : CellState()
    object Marked : CellState()
    data class Filled(val colorIndex: Int = 0) : CellState()
    data class Error(val colorIndex: Int = 0) : CellState()
}
```

#### UI Additions (Planned)
- Mode toggle: Black & White ↔ Color
- Color picker controls
- Colored clue indicators
- Color-aware validation

See `plan.md` for detailed implementation roadmap.

### Other Planned Features
- 🎯 Daily challenges
- 🏆 Achievement system
- 📊 Statistics dashboard
- 💾 Cloud save sync
- 🌐 Puzzle sharing/import
- 🎨 Custom puzzle creator
- 🔊 Sound effects & haptics

## Contributing

### Code Style
- Follow [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable names
- Add comments for complex logic
- Keep functions small and focused

### Commit Guidelines
```
type(scope): brief description

- Detailed change 1
- Detailed change 2

Examples:
feat(puzzles): add 12 new holiday puzzles
fix(game): correct auto-mark for diagonal patterns
ui(theme): update color palette for better contrast
docs(readme): add puzzle creation guide
```

### Pull Request Process
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request with detailed description

## License

[TODO: Add license information]

## Acknowledgments

- Puzzle designs inspired by classic nonogram games
- Gaming category features pixel art from iconic video games
- Built with love for puzzle enthusiasts 🧩

## Support

For questions, issues, or feature requests:
- 📧 Email: [TODO: Add contact]
- 🐛 Issues: [TODO: Add GitHub issues link]
- 💬 Discussions: [TODO: Add discussions link]

---

**Happy Puzzling! 🎨✨**
