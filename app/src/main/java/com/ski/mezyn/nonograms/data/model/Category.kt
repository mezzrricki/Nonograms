package com.ski.mezyn.nonograms.data.model

data class Category(
    val id: String,
    val name: String,
    val description: String = ""
) {
    companion object {
        val ABSTRACT = Category("abstract", "Abstract", "Simple geometric patterns")
        val ANIMALS = Category("animals", "Animals", "Cute animals and creatures")
        val NATURE = Category("nature", "Nature", "Plants, flowers, and landscapes")
        val OBJECTS = Category("objects", "Objects", "Everyday items and tools")
        val GAMING = Category("gaming", "Classic Gaming", "Iconic video game pixel art")
        val SYMBOLS = Category("symbols", "Symbols", "Icons and symbols")
        val FOOD = Category("food", "Food & Drink", "Delicious food items")
    }
}
