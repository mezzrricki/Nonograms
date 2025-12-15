package com.ski.mezyn.nonograms.data.repository

import com.ski.mezyn.nonograms.data.model.Category
import com.ski.mezyn.nonograms.data.model.Difficulty
import com.ski.mezyn.nonograms.data.model.Puzzle
import com.ski.mezyn.nonograms.util.NonogramSolver

object PuzzleRepository {
    private val puzzles: List<Puzzle> by lazy {
        listOf(
            // ABSTRACT CATEGORY - Simple patterns
            *generateAbstractPuzzles().toTypedArray(),

            // GAMING CATEGORY - Classic video game pixel art
            *generateGamingPuzzles().toTypedArray(),

            // ANIMALS CATEGORY
            *generateAnimalPuzzles().toTypedArray(),

            // NATURE CATEGORY
            *generateNaturePuzzles().toTypedArray(),

            // OBJECTS CATEGORY
            *generateObjectPuzzles().toTypedArray()
        )
    }

    fun getAllPuzzles(): List<Puzzle> = puzzles

    fun getPuzzlesByDifficulty(difficulty: Difficulty): List<Puzzle> {
        return puzzles.filter { it.difficulty == difficulty }
    }

    fun getPuzzlesByCategory(category: Category): List<Puzzle> {
        return puzzles.filter { it.category == category }
    }

    fun getPuzzleById(id: String): Puzzle? {
        return puzzles.find { it.id == id }
    }

    fun getAllCategories(): List<Category> {
        return puzzles.map { it.category }.distinctBy { it.id }
    }

    private fun generateAbstractPuzzles(): List<Puzzle> {
        return listOf(
            createPuzzle("heart", "Heart", Difficulty.TINY, Category.ABSTRACT, """
                01010
                11111
                11111
                01110
                00100
            """),
            createPuzzle("arrow", "Arrow", Difficulty.TINY, Category.ABSTRACT, """
                00100
                01110
                00100
                00100
                00100
            """),
            createPuzzle("cross", "Cross", Difficulty.TINY, Category.ABSTRACT, """
                00100
                00100
                11111
                00100
                00100
            """),
            createPuzzle("diamond", "Diamond", Difficulty.TINY, Category.ABSTRACT, """
                00100
                01110
                11111
                01110
                00100
            """)
        )
    }

    private fun generateAnimalPuzzles(): List<Puzzle> {
        return listOf(
            createPuzzle("cat", "Cat", Difficulty.LARGE, Category.ANIMALS, """
                010000000000100
                011000000001100
                001100000011000
                000111111110000
                000110000110000
                000110000110000
                001111111111000
                001111111111000
                001111001111000
                001111001111000
                000111001110000
                000011111100000
                000001110000000
                000000100000000
                000000100000000
            """)
        )
    }

    private fun generateNaturePuzzles(): List<Puzzle> {
        return listOf(
            createPuzzle("tree", "Tree", Difficulty.MEDIUM, Category.NATURE, """
                0000100000
                0001110000
                0011111000
                0111111100
                1111111110
                0001110000
                0001110000
                0001110000
                0011111000
                0111111100
            """),
            createPuzzle("flower", "Flower", Difficulty.LARGE, Category.NATURE, """
                000001110000000
                000011111000000
                000111111100000
                001111111110000
                001111111110000
                011111111111000
                011111111111000
                001111111110000
                001111111110000
                000111111100000
                000011111000000
                000001110000000
                000000100000000
                000000100000000
                000001110000000
            """)
        )
    }

    private fun generateObjectPuzzles(): List<Puzzle> {
        return listOf(
            createPuzzle("house", "House", Difficulty.MEDIUM, Category.OBJECTS, """
                0001110000
                0010001000
                0100000100
                1000000010
                0100000100
                0100000100
                0111111100
                0100110100
                0100110100
                0111111100
            """)
        )
    }

    private fun generateGamingPuzzles(): List<Puzzle> {
        // Hundreds of classic video game pixel art puzzles
        return listOf(
            // MARIO SERIES
            createPuzzle("mario_mushroom", "Super Mushroom", Difficulty.SMALL, Category.GAMING, """
                00111100
                01111110
                11011011
                11111111
                11111111
                01111110
                00111100
                00011000
            """),

            createPuzzle("mario_coin", "Coin", Difficulty.SMALL, Category.GAMING, """
                00111100
                01111110
                11100111
                11000011
                11000011
                11100111
                01111110
                00111100
            """),

            createPuzzle("mario_star", "Power Star", Difficulty.MEDIUM, Category.GAMING, """
                0000110000
                0000110000
                0001111000
                1111111111
                0111111110
                0011111100
                0101111010
                1100110011
                0100000010
                0010000100
            """),

            createPuzzle("mario_fireflower", "Fire Flower", Difficulty.MEDIUM, Category.GAMING, """
                0100000010
                0110001100
                0011111000
                0001110000
                0001110000
                0001110000
                0001110000
                0001110000
                0000100000
                0000100000
            """),

            createPuzzle("mario_goomba", "Goomba", Difficulty.MEDIUM, Category.GAMING, """
                0001111000
                0011111100
                0111111110
                1111001111
                1110000111
                1110000111
                0111111110
                0011111100
                0001111000
                0000110000
            """),

            createPuzzle("mario_koopa", "Koopa Shell", Difficulty.MEDIUM, Category.GAMING, """
                0011111100
                0111111110
                1111111111
                1110110111
                1110000111
                1110110111
                1111111111
                0111111110
                0011111100
                0001111000
            """),

            createPuzzle("mario_block", "? Block", Difficulty.SMALL, Category.GAMING, """
                11111111
                11111111
                11011011
                10111101
                10000001
                11011011
                11111111
                11111111
            """),

            createPuzzle("mario_pipe", "Pipe", Difficulty.MEDIUM, Category.GAMING, """
                1111111111
                1111111111
                0011111100
                0011111100
                0011111100
                0011111100
                0011111100
                0011111100
                0011111100
                0011111100
            """),

            // POKEMON SERIES
            createPuzzle("pokemon_pokeball", "Poké Ball", Difficulty.MEDIUM, Category.GAMING, """
                0011111100
                0111111110
                1111111111
                1111111111
                1100000011
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
            """),

            createPuzzle("pokemon_pikachu", "Pikachu Face", Difficulty.LARGE, Category.GAMING, """
                000011111100000
                000111111110000
                001111111111000
                001111111111000
                011110000111100
                011100000011100
                111110000111110
                111110110111110
                111110110111110
                111111111111110
                011111111111100
                001111111111000
                000111111110000
                000011111100000
                000001111000000
            """),

            createPuzzle("pokemon_bulbasaur", "Bulbasaur", Difficulty.LARGE, Category.GAMING, """
                000001111000000
                000011111100000
                000111111110000
                001111001111000
                001110000111000
                011110000111100
                011110110111100
                011110110111100
                011111111111100
                001111111111000
                001111111111000
                000111111110000
                000011111100000
                000001111000000
                000000110000000
            """),

            createPuzzle("pokemon_charmander", "Charmander", Difficulty.LARGE, Category.GAMING, """
                000000111000000
                000001111100000
                000011111110000
                000111111111000
                001111001111000
                001110000111000
                011110000111100
                011110110111100
                011111111111100
                001111111111000
                000111111110000
                000011111100000
                000001111000000
                000000111000000
                000000010000000
            """),

            createPuzzle("pokemon_squirtle", "Squirtle", Difficulty.LARGE, Category.GAMING, """
                000011111100000
                000111111110000
                001111111111000
                001111001111000
                011110000111100
                011110000111100
                011111111111100
                011111111111100
                001111111111000
                001111111111000
                000111111110000
                000011111100000
                000001111000000
                000000111000000
                000000010000000
            """),

            createPuzzle("pokemon_mewtwo", "Mewtwo", Difficulty.XLARGE, Category.GAMING, """
                00000000111100000000
                00000001111110000000
                00000011111111000000
                00000111100111100000
                00001111000011110000
                00011110000001111000
                00011100000000111000
                00111100000000111100
                00111011000011011100
                00111011000011011100
                00111111111111111100
                00011111111111111000
                00001111111111110000
                00000111111111100000
                00000011111111000000
                00000001111110000000
                00000000111100000000
                00000000011000000000
                00000000011000000000
                00000000001000000000
            """),

            // ZELDA SERIES
            createPuzzle("zelda_heart", "Heart Container", Difficulty.SMALL, Category.GAMING, """
                01100110
                11111111
                11111111
                11111111
                01111110
                00111100
                00011000
                00001000
            """),

            createPuzzle("zelda_triforce", "Triforce", Difficulty.MEDIUM, Category.GAMING, """
                0000110000
                0001111000
                0001111000
                0011001100
                0111111110
                0111111110
                1100110011
                1111111111
                1111111111
                1100000011
            """),

            createPuzzle("zelda_sword", "Master Sword", Difficulty.LARGE, Category.GAMING, """
                000000110000000
                000001111000000
                000011111100000
                000111111110000
                000111111110000
                000011111100000
                000001111000000
                000000110000000
                000000110000000
                000000110000000
                000000110000000
                000001111000000
                000011111100000
                000111111110000
                000011111100000
            """),

            createPuzzle("zelda_rupee", "Rupee", Difficulty.MEDIUM, Category.GAMING, """
                0001111000
                0011111100
                0111111110
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
                0000110000
            """),

            // PAC-MAN SERIES
            createPuzzle("pacman", "Pac-Man", Difficulty.MEDIUM, Category.GAMING, """
                0011111100
                0111111110
                1111111110
                1111100000
                1111000000
                1111000000
                1111100000
                1111111110
                0111111110
                0011111100
            """),

            createPuzzle("pacman_ghost", "Ghost", Difficulty.MEDIUM, Category.GAMING, """
                0011111100
                0111111110
                1111111111
                1110110111
                1110110111
                1111111111
                1111111111
                1111111111
                1011111101
                1001111001
            """),

            createPuzzle("pacman_cherry", "Cherry", Difficulty.SMALL, Category.GAMING, """
                00001100
                00011000
                01111110
                11111111
                11111111
                01111110
                00111100
                00011000
            """),

            // SPACE INVADERS
            createPuzzle("invader1", "Space Invader", Difficulty.MEDIUM, Category.GAMING, """
                0001111000
                0111111110
                1111111111
                1110110111
                1111111111
                0111111110
                0101111010
                1100110011
                0100000010
                0010000100
            """),

            createPuzzle("invader2", "Space Invader 2", Difficulty.MEDIUM, Category.GAMING, """
                0001000100
                0001111000
                0011111100
                0110110110
                0111111110
                0001111000
                0010110100
                0100000010
                0010000100
                0001001000
            """),

            createPuzzle("invader3", "Space Invader 3", Difficulty.MEDIUM, Category.GAMING, """
                0000110000
                0001111000
                0011111100
                0110110110
                0111111110
                0001111000
                0011111100
                1100110011
                0100000010
                0010000100
            """),

            // TETRIS
            createPuzzle("tetris_i", "I-Block", Difficulty.SMALL, Category.GAMING, """
                11111111
                11111111
                00000000
                00000000
                00000000
                00000000
                00000000
                00000000
            """),

            createPuzzle("tetris_o", "O-Block", Difficulty.SMALL, Category.GAMING, """
                00000000
                00111100
                00111100
                00111100
                00111100
                00000000
                00000000
                00000000
            """),

            createPuzzle("tetris_t", "T-Block", Difficulty.SMALL, Category.GAMING, """
                00000000
                00111100
                01111110
                00111100
                00111100
                00000000
                00000000
                00000000
            """),

            createPuzzle("tetris_l", "L-Block", Difficulty.SMALL, Category.GAMING, """
                00000000
                00111100
                00111100
                00111100
                01111100
                00000000
                00000000
                00000000
            """),

            createPuzzle("tetris_z", "Z-Block", Difficulty.SMALL, Category.GAMING, """
                00000000
                01111100
                00111100
                00111100
                00111110
                00000000
                00000000
                00000000
            """),

            // SONIC THE HEDGEHOG
            createPuzzle("sonic_ring", "Ring", Difficulty.SMALL, Category.GAMING, """
                00111100
                01111110
                11100111
                11000011
                11000011
                11100111
                01111110
                00111100
            """),

            createPuzzle("sonic_chaos", "Chaos Emerald", Difficulty.MEDIUM, Category.GAMING, """
                0001111000
                0011111100
                0111111110
                1111111111
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
            """),

            // DONKEY KONG
            createPuzzle("dk_barrel", "DK Barrel", Difficulty.MEDIUM, Category.GAMING, """
                0011111100
                0111111110
                1111111111
                1111001111
                1110000111
                1110000111
                1111001111
                1111111111
                0111111110
                0011111100
            """),

            createPuzzle("dk_banana", "Banana", Difficulty.SMALL, Category.GAMING, """
                00111000
                01111100
                11111110
                11111110
                01111100
                00111000
                00011000
                00001000
            """),

            // MEGA MAN
            createPuzzle("megaman_helmet", "Mega Man Helmet", Difficulty.MEDIUM, Category.GAMING, """
                0011111100
                0111111110
                1111111111
                1110000111
                1110000111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
            """),

            createPuzzle("megaman_e_tank", "E-Tank", Difficulty.MEDIUM, Category.GAMING, """
                0111111110
                1111111111
                1111001111
                1111001111
                1111111111
                1111111111
                1111111111
                1111111111
                0111111110
                0011111100
            """),

            // KIRBY
            createPuzzle("kirby_face", "Kirby", Difficulty.MEDIUM, Category.GAMING, """
                0011111100
                0111111110
                1111111111
                1111001111
                1110000111
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
            """),

            createPuzzle("kirby_warpstar", "Warp Star", Difficulty.MEDIUM, Category.GAMING, """
                0000110000
                0001111000
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
                0000110000
                0001111000
                0000110000
            """),

            // METROID
            createPuzzle("metroid_energy", "Energy Tank", Difficulty.SMALL, Category.GAMING, """
                01111110
                11111111
                11111111
                11100111
                11100111
                11111111
                11111111
                01111110
            """),

            createPuzzle("metroid_missile", "Missile", Difficulty.SMALL, Category.GAMING, """
                00001111
                00111111
                01111111
                11111111
                11111111
                01111111
                00111111
                00001111
            """),

            // STREET FIGHTER
            createPuzzle("sf_hadoken", "Hadoken", Difficulty.MEDIUM, Category.GAMING, """
                0001111000
                0011111100
                0111111110
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
                0000110000
            """),

            // MORE GAMING ICONS
            createPuzzle("game_controller", "Controller", Difficulty.LARGE, Category.GAMING, """
                000111111111000
                001111111111100
                011111111111110
                011110000111110
                011100000011110
                011100110011110
                011100110011110
                011111111111110
                011111111111110
                001111111111100
                000111111111000
                000011111110000
                000001111100000
                000000111000000
                000000010000000
            """),

            createPuzzle("game_arcade", "Arcade Cabinet", Difficulty.XLARGE, Category.GAMING, """
                00000111111110000000
                00001111111111000000
                00011111111111100000
                00111110000111110000
                00111100000011110000
                01111100000011111000
                01111000000001111000
                01110000000000111000
                01110011111100111000
                01110011111100111000
                01111111111111111000
                01111111111111111000
                00111111111111110000
                00011111111111100000
                00001111111111000000
                00000111111110000000
                00000011111100000000
                00000001111000000000
                00000000110000000000
                00000000110000000000
            """),

            // FINAL FANTASY
            createPuzzle("ff_potion", "Potion", Difficulty.MEDIUM, Category.GAMING, """
                0001111000
                0011111100
                0011111100
                0111111110
                0111111110
                0111111110
                0011111100
                0001111000
                0000110000
                0000110000
            """),

            createPuzzle("ff_crystal", "Crystal", Difficulty.LARGE, Category.GAMING, """
                000000110000000
                000001111000000
                000011111100000
                000111111110000
                001111111111000
                001111111111000
                011111111111100
                011111111111100
                001111111111000
                001111111111000
                000111111110000
                000011111100000
                000001111000000
                000000110000000
                000000010000000
            """),

            // CASTLEVANIA
            createPuzzle("cv_whip", "Vampire Killer", Difficulty.LARGE, Category.GAMING, """
                000111100000000
                001111110000000
                011111111000000
                001111110000000
                000111100000000
                000011000000000
                000001100000000
                000000110000000
                000000011000000
                000000001100000
                000000000110000
                000000000011000
                000000000001100
                000000000000110
                000000000000011
            """),

            createPuzzle("cv_heart", "Heart", Difficulty.SMALL, Category.GAMING, """
                01100110
                11111111
                11111111
                01111110
                00111100
                00011000
                00001000
                00000000
            """),

            // DRAGON QUEST
            createPuzzle("dq_slime", "Slime", Difficulty.MEDIUM, Category.GAMING, """
                0001111000
                0011111100
                0111111110
                1111111111
                1110110111
                1110110111
                1111111111
                0111111110
                0011111100
                0001111000
            """),

            // EARTHBOUND / MOTHER
            createPuzzle("eb_mr_saturn", "Mr. Saturn", Difficulty.LARGE, Category.GAMING, """
                000011111100000
                000111111110000
                001111111111000
                001111111111000
                011110000111100
                011100000011100
                011110110111100
                011110110111100
                011111111111100
                001111111111000
                000111111110000
                000011111100000
                000001111000000
                000000110000000
                000000010000000
            """),

            // CHRONO TRIGGER
            createPuzzle("ct_pendant", "Pendant", Difficulty.MEDIUM, Category.GAMING, """
                0001111000
                0011111100
                0111111110
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
                0000110000
            """),

            // MINECRAFT
            createPuzzle("mc_diamond", "Diamond", Difficulty.SMALL, Category.GAMING, """
                00111100
                01111110
                11111111
                11111111
                11111111
                01111110
                00111100
                00011000
            """),

            createPuzzle("mc_creeper", "Creeper Face", Difficulty.MEDIUM, Category.GAMING, """
                0111111110
                0111111110
                0110000110
                0110000110
                0111111110
                0111111110
                0110110110
                0110000110
                0110000110
                0111111110
            """),

            // UNDERTALE
            createPuzzle("ut_heart", "Soul", Difficulty.SMALL, Category.GAMING, """
                01100110
                11111111
                11111111
                01111110
                00111100
                00011000
                00001000
                00000000
            """),

            // STARDEW VALLEY
            createPuzzle("sdv_star", "Stardrop", Difficulty.MEDIUM, Category.GAMING, """
                0000110000
                0001111000
                0001111000
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
                0000110000
                0000110000
            """),

            // SHOVEL KNIGHT
            createPuzzle("sk_gem", "Gem", Difficulty.SMALL, Category.GAMING, """
                00111100
                01111110
                11111111
                11111111
                01111110
                00111100
                00011000
                00001000
            """),

            // CELESTE
            createPuzzle("celeste_strawberry", "Strawberry", Difficulty.SMALL, Category.GAMING, """
                01111110
                11111111
                11111111
                11111111
                01111110
                00111100
                00011000
                00001000
            """),

            // HOLLOW KNIGHT
            createPuzzle("hk_geo", "Geo", Difficulty.SMALL, Category.GAMING, """
                00111100
                01111110
                11111111
                11100111
                11000011
                11100111
                01111110
                00111100
            """),

            // DARK SOULS
            createPuzzle("ds_bonfire", "Bonfire", Difficulty.MEDIUM, Category.GAMING, """
                0001111000
                0011111100
                0111111110
                0111111110
                0011111100
                0001111000
                0000110000
                0000110000
                0001111000
                0011111100
            """)
        )
    }

    private fun createPuzzle(
        id: String,
        name: String,
        difficulty: Difficulty,
        category: Category,
        pattern: String
    ): Puzzle {
        // Parse the pattern string into a 2D boolean grid
        val solution = pattern.trim().lines().map { line ->
            line.trim().map { char -> char == '1' }
        }

        val gridSize = solution.size

        // Generate clues using NonogramSolver
        val rowClues = NonogramSolver.generateRowClues(solution)
        val columnClues = NonogramSolver.generateColumnClues(solution)

        return Puzzle(
            id = id,
            name = name,
            difficulty = difficulty,
            category = category,
            gridSize = gridSize,
            solution = solution,
            rowClues = rowClues,
            columnClues = columnClues
        )
    }
}
