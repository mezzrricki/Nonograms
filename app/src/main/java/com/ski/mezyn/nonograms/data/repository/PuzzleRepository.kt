package com.ski.mezyn.nonograms.data.repository

import androidx.compose.ui.graphics.Color
import com.ski.mezyn.nonograms.data.model.Category
import com.ski.mezyn.nonograms.data.model.ColorClue
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
            *generateObjectPuzzles().toTypedArray(),

            // SYMBOLS CATEGORY
            *generateSymbolsPuzzles().toTypedArray(),

            // FOOD CATEGORY
            *generateFoodPuzzles().toTypedArray(),

            // TRANSPORTATION CATEGORY
            *generateTransportationPuzzles().toTypedArray(),

            // MUSIC & ARTS CATEGORY
            *generateMusicArtsPuzzles().toTypedArray(),

            // SPORTS CATEGORY
            *generateSportsPuzzles().toTypedArray(),

            // HOLIDAYS CATEGORY
            *generateHolidaysPuzzles().toTypedArray(),

            // COLOR PUZZLES CATEGORY - Test color puzzles
            *generateColorPuzzles().toTypedArray()
        )
    }

    fun getAllPuzzles(): List<Puzzle> = puzzles

    fun getPuzzleById(id: String): Puzzle? {
        return puzzles.find { it.id == id }
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
            """),
            createPuzzle("circle", "Circle", Difficulty.SMALL, Category.ABSTRACT, """
                01111110
                11111111
                11111111
                11111111
                11111111
                11111111
                11111111
                01111110
            """),
            createPuzzle("triangle", "Triangle", Difficulty.SMALL, Category.ABSTRACT, """
                00011000
                00111100
                01111110
                11111111
                11111111
                11111111
                11111111
                11111111
            """),
            createPuzzle("spiral", "Spiral", Difficulty.MEDIUM, Category.ABSTRACT, """
                1111111110
                0000000110
                0111110110
                0100010110
                0101110110
                0100000110
                0111111110
                0000000000
                0000000000
                0000000000
            """),
            createPuzzle("checkered", "Checkered Pattern", Difficulty.SMALL, Category.ABSTRACT, """
                10101010
                01010101
                10101010
                01010101
                10101010
                01010101
                10101010
                01010101
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
            """),
            createPuzzle("butterfly", "Butterfly", Difficulty.MEDIUM, Category.ANIMALS, """
                1010000101
                1111001111
                1111111111
                0111111110
                0011111100
                0011111100
                0111111110
                1111111111
                1111001111
                1010000101
            """),
            createPuzzle("bird", "Bird", Difficulty.SMALL, Category.ANIMALS, """
                00011000
                00111100
                01111110
                11111111
                11111111
                01111110
                00111100
                00011000
            """),
            createPuzzle("fish", "Fish", Difficulty.SMALL, Category.ANIMALS, """
                00011110
                00111111
                01111111
                11111111
                11111111
                01111111
                00111111
                00011110
            """),
            createPuzzle("rabbit", "Rabbit", Difficulty.MEDIUM, Category.ANIMALS, """
                0100000010
                0110000110
                0111111110
                0111111110
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
            """),
            createPuzzle("dog", "Dog", Difficulty.LARGE, Category.ANIMALS, """
                100000000001000
                110000000011000
                011111111110000
                001111111111000
                001111001111000
                001111001111000
                001111111111000
                001111111111000
                000111111110000
                000011111100000
                000001111000000
                000000110000000
                000001111000000
                000001111000000
                000000110000000
            """),
            createPuzzle("turtle", "Turtle", Difficulty.MEDIUM, Category.ANIMALS, """
                0001111000
                0011111100
                0111111110
                1111111111
                1111111111
                1111111111
                0111111110
                0011111100
                1010001010
                1010001010
            """),
            createPuzzle("elephant", "Elephant", Difficulty.LARGE, Category.ANIMALS, """
                000011111100000
                000111111110000
                001111111111000
                011111111111100
                011110000111100
                011110000111100
                011111111111100
                001111111111000
                001111111111000
                000111111110000
                000011111100000
                000001111000000
                000000110000000
                000001111000000
                000001111000000
            """),
            createPuzzle("horse", "Horse", Difficulty.LARGE, Category.ANIMALS, """
                000011111100000
                000111111110000
                001111111111000
                001111001111000
                001111001111000
                001111111111000
                001111111111000
                000111111110000
                000011111100000
                000001111000000
                000000110000000
                000001111000000
                000011111100000
                000011111100000
                000001111000000
            """),
            createPuzzle("dolphin", "Dolphin", Difficulty.MEDIUM, Category.ANIMALS, """
                0000111110
                0001111111
                0011111111
                0111111111
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
            """),
            createPuzzle("lion", "Lion", Difficulty.XLARGE, Category.ANIMALS, """
                01000000000000010
                01100000000000110
                00110000000001100
                00011111111110000
                00111111111111000
                01111111111111100
                01111100011111100
                01111100011111100
                01111111111111100
                00111111111111000
                00011111111110000
                00001111111100000
                00000111111000000
                00000011110000000
                00000001100000000
                00000001100000000
                00000000000000000
            """),
            createPuzzle("giraffe", "Giraffe", Difficulty.XLARGE, Category.ANIMALS, """
                00000001111000000
                00000011111100000
                00000111111110000
                00001111111111000
                00001111001111000
                00001111001111000
                00000111111110000
                00000011111100000
                00000001111000000
                00000001111000000
                00000001111000000
                00000001111000000
                00000011111100000
                00000111111110000
                00001111111111000
                00011111111111100
                00001111111111000
                00000111111110000
                00000011111100000
                00000001111000000
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
            """),
            createPuzzle("leaf", "Leaf", Difficulty.SMALL, Category.NATURE, """
                00011110
                00111111
                01111111
                11111110
                11111100
                01111000
                00110000
                00010000
            """),
            createPuzzle("acorn", "Acorn", Difficulty.SMALL, Category.NATURE, """
                01111110
                11111111
                11111111
                01111110
                01111110
                01111110
                00111100
                00011000
            """),
            createPuzzle("mushroom", "Mushroom", Difficulty.SMALL, Category.NATURE, """
                01111110
                11111111
                11111111
                01111110
                00111100
                00111100
                00111100
                00111100
            """),
            createPuzzle("cactus", "Cactus", Difficulty.MEDIUM, Category.NATURE, """
                0001111000
                0011111100
                0111111110
                0111111110
                0011111100
                0011111100
                0011111100
                0011111100
                0011111100
                0011111100
            """),
            createPuzzle("sun", "Sun", Difficulty.MEDIUM, Category.NATURE, """
                0100000010
                0010001000
                0001111000
                0011111100
                1111111111
                1111111111
                0011111100
                0001111000
                0010001000
                0100000010
            """),
            createPuzzle("moon", "Crescent Moon", Difficulty.SMALL, Category.NATURE, """
                00111100
                01111000
                11110000
                11100000
                11100000
                11110000
                01111000
                00111100
            """),
            createPuzzle("mountain", "Mountain", Difficulty.MEDIUM, Category.NATURE, """
                0000110000
                0001111000
                0011111100
                0111111110
                1111111111
                1111111111
                1111111111
                1111111111
                1111111111
                1111111111
            """),
            createPuzzle("cloud", "Cloud", Difficulty.SMALL, Category.NATURE, """
                00111100
                01111110
                11111111
                11111111
                11111111
                11111111
                01111110
                00111100
            """),
            createPuzzle("rainbow", "Rainbow", Difficulty.MEDIUM, Category.NATURE, """
                0011111100
                0111111110
                1111111111
                1110000111
                1100000011
                1000000001
                0000000000
                0000000000
                0000000000
                0000000000
            """),
            createPuzzle("palm_tree", "Palm Tree", Difficulty.LARGE, Category.NATURE, """
                100000000001000
                110000000011000
                011111111110000
                001111111100000
                000111111000000
                000011110000000
                000001100000000
                000001100000000
                000001100000000
                000011110000000
                000111111000000
                001111111100000
                011111111110000
                001111111100000
                000111111000000
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
            """),
            createPuzzle("key", "Key", Difficulty.SMALL, Category.OBJECTS, """
                01111100
                11111110
                11111110
                01111100
                00110000
                00110000
                00110000
                00110000
            """),
            createPuzzle("lock", "Lock", Difficulty.SMALL, Category.OBJECTS, """
                01111110
                11111111
                11000011
                11111111
                11111111
                11111111
                11111111
                01111110
            """),
            createPuzzle("cup", "Cup", Difficulty.SMALL, Category.OBJECTS, """
                10000001
                01111110
                01111110
                01111110
                01111110
                01111110
                01111110
                00111100
            """),
            createPuzzle("book", "Book", Difficulty.MEDIUM, Category.OBJECTS, """
                0111111110
                1111111111
                1111111111
                1111001111
                1111001111
                1111111111
                1111111111
                1111111111
                1111111111
                0111111110
            """),
            createPuzzle("umbrella", "Umbrella", Difficulty.MEDIUM, Category.OBJECTS, """
                0011111100
                0111111110
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
                0000110000
                0001111000
            """),
            createPuzzle("lightbulb", "Light Bulb", Difficulty.SMALL, Category.OBJECTS, """
                01111110
                11111111
                11111111
                11111111
                01111110
                00111100
                00111100
                00011000
            """),
            createPuzzle("camera", "Camera", Difficulty.MEDIUM, Category.OBJECTS, """
                0111111110
                1111111111
                1111111111
                1110110111
                1111111111
                1111111111
                1111111111
                1111111111
                0111111110
                0011111100
            """),
            createPuzzle("phone", "Phone", Difficulty.MEDIUM, Category.OBJECTS, """
                0111111110
                1111111111
                1111111111
                1111111111
                1111111111
                1111111111
                1111111111
                1111111111
                0111111110
                0011111100
            """),
            createPuzzle("chair", "Chair", Difficulty.MEDIUM, Category.OBJECTS, """
                1111111110
                1111111110
                0000110000
                0000110000
                0000110000
                0111111100
                0111111100
                0011001100
                0011001100
                0011001100
            """),
            createPuzzle("scissors", "Scissors", Difficulty.SMALL, Category.OBJECTS, """
                01100110
                11111111
                11111111
                01111110
                00111100
                00111100
                01111110
                11111111
            """),
            createPuzzle("lamp", "Lamp", Difficulty.MEDIUM, Category.OBJECTS, """
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

    private fun generateHolidaysPuzzles(): List<Puzzle> {
        return listOf(
            createPuzzle("holiday_snowflake", "Snowflake", Difficulty.TINY, Category.HOLIDAYS, """
                10101
                01110
                11111
                01110
                10101
            """),
            createPuzzle("holiday_pumpkin", "Pumpkin", Difficulty.TINY, Category.HOLIDAYS, """
                00100
                01110
                11111
                11111
                01110
            """),
            createPuzzle("holiday_heart", "Valentine Heart", Difficulty.TINY, Category.HOLIDAYS, """
                01010
                11111
                11111
                01110
                00100
            """),
            createPuzzle("holiday_easter_egg", "Easter Egg", Difficulty.TINY, Category.HOLIDAYS, """
                01110
                11111
                11111
                11111
                01110
            """),
            createPuzzle("holiday_christmas_tree", "Christmas Tree", Difficulty.SMALL, Category.HOLIDAYS, """
                00011000
                00111100
                01111110
                11111111
                01111110
                01111110
                00111100
                00111100
            """),
            createPuzzle("holiday_gift_box", "Gift Box", Difficulty.SMALL, Category.HOLIDAYS, """
                11111111
                01111110
                01111110
                01111110
                01111110
                01111110
                01111110
                11111111
            """),
            createPuzzle("holiday_ghost", "Ghost", Difficulty.SMALL, Category.HOLIDAYS, """
                01111110
                11111111
                11011011
                11111111
                11111111
                11111111
                10111101
                10011001
            """),
            createPuzzle("holiday_bunny", "Easter Bunny", Difficulty.SMALL, Category.HOLIDAYS, """
                10100101
                10100101
                01111110
                01111110
                11111111
                11111111
                01111110
                00111100
            """),
            createPuzzle("holiday_menorah", "Menorah", Difficulty.MEDIUM, Category.HOLIDAYS, """
                0101010101
                0111111110
                0111111110
                0011111100
                0001111000
                0001111000
                0001111000
                0011111100
                0111111110
                0111111110
            """),
            createPuzzle("holiday_jack_o_lantern", "Jack-o'-Lantern", Difficulty.MEDIUM, Category.HOLIDAYS, """
                0001111000
                0011111100
                0111111110
                0111111110
                1110110111
                1111111111
                1111001111
                0111111110
                0011111100
                0001111000
            """),
            createPuzzle("holiday_turkey", "Turkey", Difficulty.MEDIUM, Category.HOLIDAYS, """
                0111111110
                1111111111
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
                0001111000
                0001111000
            """),
            createPuzzle("holiday_shamrock", "Shamrock", Difficulty.MEDIUM, Category.HOLIDAYS, """
                0010001000
                0111111100
                1111111110
                1111111110
                0111111100
                0001111000
                0000110000
                0000110000
                0001111000
                0001111000
            """),
            createPuzzle("holiday_fireworks", "Fireworks", Difficulty.MEDIUM, Category.HOLIDAYS, """
                1000000001
                0100110010
                0010110100
                0001111000
                0011111100
                0001111000
                0010110100
                0100110010
                1000000001
                0000110000
            """),
            createPuzzle("holiday_snowman", "Snowman", Difficulty.LARGE, Category.HOLIDAYS, """
                000011111100000
                000111111110000
                001111111111000
                001111001111000
                001111111111000
                000111111110000
                000011111100000
                001111111111000
                011111111111100
                011111111111100
                001111111111000
                000111111110000
                000011111100000
                000001111000000
                000000110000000
            """),
            createPuzzle("holiday_wreath", "Christmas Wreath", Difficulty.LARGE, Category.HOLIDAYS, """
                000011111100000
                000111111110000
                001111001111000
                011110000111100
                011100000011100
                111100000011110
                111100000011110
                111100000011110
                011100000011100
                011110000111100
                001111001111000
                000111111110000
                000011111100000
                000001111000000
                000000110000000
            """)
        )
    }

    private fun generateSportsPuzzles(): List<Puzzle> {
        return listOf(
            createPuzzle("sports_soccer", "Soccer Ball", Difficulty.TINY, Category.SPORTS, """
                01110
                11111
                11111
                11111
                01110
            """),
            createPuzzle("sports_baseball", "Baseball", Difficulty.TINY, Category.SPORTS, """
                01110
                11011
                11111
                11011
                01110
            """),
            createPuzzle("sports_basketball", "Basketball", Difficulty.SMALL, Category.SPORTS, """
                01111110
                11111111
                11111111
                11100111
                11100111
                11111111
                11111111
                01111110
            """),
            createPuzzle("sports_tennis_racket", "Tennis Racket", Difficulty.SMALL, Category.SPORTS, """
                01111110
                11111111
                11011011
                11011011
                01111110
                00111100
                00011000
                00011000
            """),
            createPuzzle("sports_golf_ball", "Golf Ball", Difficulty.TINY, Category.SPORTS, """
                01110
                11111
                11011
                11111
                01110
            """),
            createPuzzle("sports_football", "Football", Difficulty.MEDIUM, Category.SPORTS, """
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
            createPuzzle("sports_hockey_stick", "Hockey Stick", Difficulty.MEDIUM, Category.SPORTS, """
                1111111110
                1111111110
                0000110000
                0000110000
                0000110000
                0000110000
                0000110000
                0001111000
                0011111100
                0011111100
            """),
            createPuzzle("sports_bowling_pin", "Bowling Pin", Difficulty.MEDIUM, Category.SPORTS, """
                0001111000
                0011111100
                0011111100
                0001111000
                0001111000
                0011111100
                0111111110
                0111111110
                0011111100
                0001111000
            """),
            createPuzzle("sports_trophy", "Trophy", Difficulty.MEDIUM, Category.SPORTS, """
                1010001010
                1110001110
                0111111110
                0111111110
                0011111100
                0001111000
                0001111000
                0011111100
                0111111110
                0111111110
            """),
            createPuzzle("sports_dumbbell", "Dumbbell", Difficulty.SMALL, Category.SPORTS, """
                11000011
                11111111
                11111111
                00111100
                00111100
                11111111
                11111111
                11000011
            """),
            createPuzzle("sports_medal", "Medal", Difficulty.SMALL, Category.SPORTS, """
                10100101
                01111110
                01111110
                11111111
                11111111
                11111111
                01111110
                00111100
            """),
            createPuzzle("sports_volleyball", "Volleyball", Difficulty.SMALL, Category.SPORTS, """
                01111110
                11111111
                11001111
                11000111
                11100011
                11110001
                11111111
                01111110
            """)
        )
    }

    private fun generateMusicArtsPuzzles(): List<Puzzle> {
        return listOf(
            createPuzzle("music_note", "Music Note", Difficulty.TINY, Category.MUSIC_ARTS, """
                00110
                01110
                01110
                00110
                00110
            """),
            createPuzzle("music_treble_clef", "Treble Clef", Difficulty.TINY, Category.MUSIC_ARTS, """
                00110
                01110
                11110
                01110
                01110
            """),
            createPuzzle("music_guitar", "Guitar", Difficulty.SMALL, Category.MUSIC_ARTS, """
                00111100
                01111110
                01111110
                00111100
                00011000
                00011000
                00111100
                01111110
            """),
            createPuzzle("music_microphone", "Microphone", Difficulty.SMALL, Category.MUSIC_ARTS, """
                01111110
                11111111
                11111111
                01111110
                00111100
                00011000
                00011000
                00011000
            """),
            createPuzzle("music_paintbrush", "Paint Brush", Difficulty.SMALL, Category.MUSIC_ARTS, """
                11111100
                01111110
                00111110
                00011110
                00001110
                00000110
                00000110
                00000010
            """),
            createPuzzle("music_piano_keys", "Piano Keys", Difficulty.MEDIUM, Category.MUSIC_ARTS, """
                1111111111
                1010101010
                1010101010
                1010101010
                1010101010
                1111111111
                1111111111
                1111111111
                1111111111
                1111111111
            """),
            createPuzzle("music_violin", "Violin", Difficulty.MEDIUM, Category.MUSIC_ARTS, """
                0001111000
                0011111100
                0111111110
                0111111110
                0011111100
                0001111000
                0000110000
                0000110000
                0001111000
                0001111000
            """),
            createPuzzle("music_drum", "Drum", Difficulty.MEDIUM, Category.MUSIC_ARTS, """
                0111111110
                1111111111
                1111111111
                1111111111
                1111111111
                1111111111
                1111111111
                1111111111
                0111111110
                0011111100
            """),
            createPuzzle("music_palette", "Art Palette", Difficulty.MEDIUM, Category.MUSIC_ARTS, """
                0111111110
                1111111111
                1111111111
                1110111011
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
            """),
            createPuzzle("music_saxophone", "Saxophone", Difficulty.LARGE, Category.MUSIC_ARTS, """
                000011111100000
                000111111110000
                001111111111000
                001111001111000
                001111001111000
                000111111110000
                000011111100000
                000001111000000
                000000110000000
                000001111000000
                000011111100000
                000111111110000
                001111111111000
                000111111110000
                000011111100000
            """),
            createPuzzle("music_harp", "Harp", Difficulty.LARGE, Category.MUSIC_ARTS, """
                000000000000110
                000000000001110
                000000000011110
                000000000111110
                100000001111110
                110000011111110
                111000111111110
                111101111111110
                111111111111110
                011111111111100
                001111111111000
                000111111110000
                000011111100000
                000001111000000
                000000110000000
            """),
            createPuzzle("music_trumpet", "Trumpet", Difficulty.MEDIUM, Category.MUSIC_ARTS, """
                0000011111
                0000111111
                0001111111
                0011111110
                0111111100
                1111111000
                1111110000
                0111111000
                0011111100
                0001111110
            """)
        )
    }

    private fun generateTransportationPuzzles(): List<Puzzle> {
        return listOf(
            createPuzzle("transport_arrow_sign", "Arrow Sign", Difficulty.TINY, Category.TRANSPORTATION, """
                00100
                01110
                11111
                00100
                00100
            """),
            createPuzzle("transport_stop_sign", "Stop Sign", Difficulty.TINY, Category.TRANSPORTATION, """
                01110
                11111
                11111
                11111
                01110
            """),
            createPuzzle("transport_bicycle", "Bicycle", Difficulty.SMALL, Category.TRANSPORTATION, """
                00011000
                00111100
                01111110
                01100110
                01100110
                01100110
                00100100
                00100100
            """),
            createPuzzle("transport_car", "Car", Difficulty.SMALL, Category.TRANSPORTATION, """
                00111100
                01111110
                11111111
                11111111
                11111111
                01111110
                01011010
                01011010
            """),
            createPuzzle("transport_sailboat", "Sailboat", Difficulty.SMALL, Category.TRANSPORTATION, """
                00001000
                00011000
                00111000
                01111000
                11111000
                01111110
                11111111
                01111110
            """),
            createPuzzle("transport_airplane", "Airplane", Difficulty.MEDIUM, Category.TRANSPORTATION, """
                0000110000
                0001111000
                0011111100
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
                0000010000
            """),
            createPuzzle("transport_train", "Train", Difficulty.MEDIUM, Category.TRANSPORTATION, """
                0111111110
                1111111111
                1111111111
                1110110111
                1110110111
                1111111111
                1111111111
                0111111110
                0110000110
                0110000110
            """),
            createPuzzle("transport_rocket", "Rocket", Difficulty.MEDIUM, Category.TRANSPORTATION, """
                0000110000
                0001111000
                0011111100
                0111111110
                0111111110
                0011111100
                0001111000
                0000110000
                0001111000
                0001111000
            """),
            createPuzzle("transport_hot_air_balloon", "Hot Air Balloon", Difficulty.MEDIUM, Category.TRANSPORTATION, """
                0011111100
                0111111110
                1111111111
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
                0000110000
            """),
            createPuzzle("transport_cruise_ship", "Cruise Ship", Difficulty.LARGE, Category.TRANSPORTATION, """
                000011111100000
                000111111110000
                001111111111000
                011111111111100
                111111111111110
                111111111111110
                111111111111110
                111111111111110
                011111111111100
                001111111111000
                000111111110000
                000011111100000
                000001111000000
                000000110000000
                000000010000000
            """),
            createPuzzle("transport_helicopter", "Helicopter", Difficulty.LARGE, Category.TRANSPORTATION, """
                111111111111111
                000000110000000
                000001111000000
                000011111100000
                000111111110000
                001111111111000
                001111111111000
                000111111110000
                000011111100000
                000001111000000
                000000110000000
                000000110000000
                000001111000000
                000000110000000
                000000010000000
            """),
            createPuzzle("transport_locomotive", "Steam Locomotive", Difficulty.XLARGE, Category.TRANSPORTATION, """
                00000001111111000000
                00000011111111100000
                00000111111111110000
                00001111111111111000
                00011111100011111100
                00111111000001111110
                00111110000000111110
                01111110000000111111
                01111110011001111111
                01111111111111111111
                01111111111111111111
                00111111111111111110
                00011111111111111100
                00001111111111111000
                00000111111111110000
                00000011111111100000
                00000001111111000000
                00000000111110000000
                00000000011100000000
                00000000001000000000
            """)
        )
    }

    private fun generateFoodPuzzles(): List<Puzzle> {
        return listOf(
            createPuzzle("food_apple", "Apple", Difficulty.SMALL, Category.FOOD, """
                00111100
                00111100
                01111110
                11111111
                11111111
                11111111
                01111110
                00111100
            """),
            createPuzzle("food_cherry", "Cherry", Difficulty.TINY, Category.FOOD, """
                01010
                11111
                01110
                01110
                00100
            """),
            createPuzzle("food_grapes", "Grapes", Difficulty.MEDIUM, Category.FOOD, """
                0001111000
                0011111100
                0111111110
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
                0000010000
            """),
            createPuzzle("food_pizza", "Pizza Slice", Difficulty.MEDIUM, Category.FOOD, """
                0000000010
                0000001110
                0000111110
                0001111110
                0011111110
                0111111110
                0111101110
                1111111110
                1111111110
                1111111100
            """),
            createPuzzle("food_icecream", "Ice Cream Cone", Difficulty.MEDIUM, Category.FOOD, """
                0011111100
                0111111110
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
                0000110000
                0000010000
            """),
            createPuzzle("food_donut", "Donut", Difficulty.SMALL, Category.FOOD, """
                01111110
                11111111
                11100111
                11000011
                11000011
                11100111
                11111111
                01111110
            """),
            createPuzzle("food_hamburger", "Hamburger", Difficulty.MEDIUM, Category.FOOD, """
                0111111110
                1111111111
                1111111111
                0111111110
                0011111100
                0111111110
                1111111111
                1111111111
                0111111110
                0011111100
            """),
            createPuzzle("food_coffee", "Coffee Cup", Difficulty.SMALL, Category.FOOD, """
                11000000
                01111110
                01111110
                01111110
                01111110
                01111110
                01111110
                00111100
            """),
            createPuzzle("food_watermelon", "Watermelon Slice", Difficulty.MEDIUM, Category.FOOD, """
                0011111100
                0111111110
                1111111111
                1111111111
                1110110111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
            """),
            createPuzzle("food_cupcake", "Cupcake", Difficulty.SMALL, Category.FOOD, """
                00111100
                01111110
                11111111
                11111111
                01111110
                01111110
                01111110
                00111100
            """),
            createPuzzle("food_cake", "Birthday Cake", Difficulty.LARGE, Category.FOOD, """
                000001110000000
                000011111000000
                000011111000000
                001111111111000
                011111111111100
                111111111111110
                111111111111110
                111111111111110
                111111111111110
                011111111111100
                001111111111000
                000111111110000
                000011111100000
                000001111000000
                000000110000000
            """),
            createPuzzle("food_strawberry", "Strawberry", Difficulty.SMALL, Category.FOOD, """
                01111110
                11111111
                11111111
                11111111
                11101111
                01111110
                00111100
                00011000
            """)
        )
    }

    private fun generateSymbolsPuzzles(): List<Puzzle> {
        return listOf(
            createPuzzle("symbol_star", "Star", Difficulty.TINY, Category.SYMBOLS, """
                00100
                01110
                11111
                01110
                10101
            """),
            createPuzzle("symbol_smiley", "Smiley Face", Difficulty.SMALL, Category.SYMBOLS, """
                01111110
                11111111
                11011011
                11111111
                10111101
                11000011
                01111110
                00111100
            """),
            createPuzzle("symbol_peace", "Peace Sign", Difficulty.MEDIUM, Category.SYMBOLS, """
                0011111100
                0111111110
                1111011111
                1111011111
                1111011111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
            """),
            createPuzzle("symbol_anchor", "Anchor", Difficulty.MEDIUM, Category.SYMBOLS, """
                0000110000
                0001111000
                0001111000
                0000110000
                0000110000
                0000110000
                0011111100
                0111111110
                1111111111
                1110000111
            """),
            createPuzzle("symbol_crown", "Crown", Difficulty.MEDIUM, Category.SYMBOLS, """
                1010001010
                1110001110
                1111111110
                0111111100
                0111111100
                0111111100
                0111111100
                0011111000
                0011111000
                0001110000
            """),
            createPuzzle("symbol_lightning", "Lightning Bolt", Difficulty.SMALL, Category.SYMBOLS, """
                01111000
                01110000
                00111000
                00111100
                00011100
                00111000
                01110000
                11100000
            """),
            createPuzzle("symbol_hourglass", "Hourglass", Difficulty.SMALL, Category.SYMBOLS, """
                11111111
                11111111
                01111110
                00111100
                00011000
                00111100
                01111110
                11111111
            """),
            createPuzzle("symbol_shield", "Shield", Difficulty.MEDIUM, Category.SYMBOLS, """
                0111111110
                1111111111
                1111111111
                1111111111
                1111111111
                0111111110
                0011111100
                0001111000
                0000110000
                0000010000
            """),
            createPuzzle("symbol_infinity", "Infinity", Difficulty.SMALL, Category.SYMBOLS, """
                01100110
                11111111
                11011011
                11011011
                11111111
                01100110
                00000000
                00000000
            """),
            createPuzzle("symbol_compass", "Compass Rose", Difficulty.LARGE, Category.SYMBOLS, """
                000000110000000
                000001111000000
                000001111000000
                000000110000000
                011000110000110
                001100110001100
                000110110011000
                111111111111111
                000110110011000
                001100110001100
                011000110000110
                000000110000000
                000001111000000
                000001111000000
                000000110000000
            """),
            createPuzzle("symbol_sun", "Sun", Difficulty.MEDIUM, Category.SYMBOLS, """
                0100000010
                0010001000
                0001111000
                0011111100
                1111111111
                1111111111
                0011111100
                0001111000
                0010001000
                0100000010
            """),
            createPuzzle("symbol_moon", "Crescent Moon", Difficulty.SMALL, Category.SYMBOLS, """
                00111100
                01111000
                11110000
                11100000
                11100000
                11110000
                01111000
                00111100
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
        // Parse the pattern string into a 2D int grid (0 = empty, 1 = black/filled)
        val solution = pattern.trim().lines().map { line ->
            line.trim().map { char -> if (char == '1') 1 else 0 }
        }

        val gridSize = solution.size

        // Generate clues using NonogramSolver (converted to ColorClue format)
        val booleanSolution = solution.map { row -> row.map { it == 1 } }
        val rowCluesInt = NonogramSolver.generateRowClues(booleanSolution)
        val columnCluesInt = NonogramSolver.generateColumnClues(booleanSolution)

        // Convert to ColorClue format (all with colorIndex = 1 for black)
        val rowClues = rowCluesInt.map { row ->
            row.map { count -> ColorClue(count, colorIndex = 1) }
        }
        val columnClues = columnCluesInt.map { col ->
            col.map { count -> ColorClue(count, colorIndex = 1) }
        }

        return Puzzle(
            id = id,
            name = name,
            difficulty = difficulty,
            category = category,
            gridSize = gridSize,
            solution = solution,
            colorPalette = null, // B&W puzzle
            rowClues = rowClues,
            columnClues = columnClues
        )
    }

    private fun generateColorPuzzles(): List<Puzzle> {
        return listOf(
            createColorPuzzle(
                id = "color_rainbow_heart",
                name = "Rainbow Heart",
                difficulty = Difficulty.SMALL,
                category = Category.ABSTRACT,
                colorPalette = listOf(
                    Color(0xFFFF0000), // Red
                    Color(0xFFFF7F00), // Orange
                    Color(0xFFFFFF00)  // Yellow
                ),
                solution = """
                    01100110
                    12211221
                    12222221
                    12222221
                    01222210
                    00122100
                    00011000
                    00001000
                """
            ),
            createColorPuzzle(
                id = "color_simple_test",
                name = "Color Test",
                difficulty = Difficulty.TINY,
                category = Category.ABSTRACT,
                colorPalette = listOf(
                    Color(0xFFFF0000), // Red
                    Color(0xFF0000FF)  // Blue
                ),
                solution = """
                    11111
                    10001
                    10201
                    10001
                    11111
                """
            )
        )
    }

    private fun createColorPuzzle(
        id: String,
        name: String,
        difficulty: Difficulty,
        category: Category,
        colorPalette: List<Color>,
        solution: String
    ): Puzzle {
        // Parse the pattern string into a 2D int grid
        // 0 = empty, 1+ = color palette index
        val solutionGrid = solution.trim().lines().map { line ->
            line.trim().map { char -> char.toString().toIntOrNull() ?: 0 }
        }

        val gridSize = solutionGrid.size

        // Generate clues manually for color puzzles
        // Row clues
        val rowClues = solutionGrid.map { row ->
            val clues = mutableListOf<ColorClue>()
            var currentCount = 0
            var currentColor = 0

            row.forEach { cell ->
                if (cell != 0) {
                    if (cell == currentColor) {
                        currentCount++
                    } else {
                        if (currentCount > 0) {
                            clues.add(ColorClue(currentCount, currentColor))
                        }
                        currentColor = cell
                        currentCount = 1
                    }
                } else {
                    if (currentCount > 0) {
                        clues.add(ColorClue(currentCount, currentColor))
                        currentCount = 0
                        currentColor = 0
                    }
                }
            }

            if (currentCount > 0) {
                clues.add(ColorClue(currentCount, currentColor))
            }

            if (clues.isEmpty()) listOf(ColorClue(0, 0)) else clues
        }

        // Column clues
        val columnClues = (0 until gridSize).map { colIndex ->
            val column = solutionGrid.map { it[colIndex] }
            val clues = mutableListOf<ColorClue>()
            var currentCount = 0
            var currentColor = 0

            column.forEach { cell ->
                if (cell != 0) {
                    if (cell == currentColor) {
                        currentCount++
                    } else {
                        if (currentCount > 0) {
                            clues.add(ColorClue(currentCount, currentColor))
                        }
                        currentColor = cell
                        currentCount = 1
                    }
                } else {
                    if (currentCount > 0) {
                        clues.add(ColorClue(currentCount, currentColor))
                        currentCount = 0
                        currentColor = 0
                    }
                }
            }

            if (currentCount > 0) {
                clues.add(ColorClue(currentCount, currentColor))
            }

            if (clues.isEmpty()) listOf(ColorClue(0, 0)) else clues
        }

        return Puzzle(
            id = id,
            name = name,
            difficulty = difficulty,
            category = category,
            gridSize = gridSize,
            solution = solutionGrid,
            colorPalette = colorPalette,
            rowClues = rowClues,
            columnClues = columnClues
        )
    }
}
