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
        val TRANSPORTATION = Category("transportation", "Transportation", "Vehicles and ways to travel")
        val MUSIC_ARTS = Category("music_arts", "Music & Arts", "Musical instruments and artistic items")
        val SPORTS = Category("sports", "Sports & Activities", "Sports equipment and activities")
        val HOLIDAYS = Category("holidays", "Holidays & Seasons", "Festive themes and seasonal items")
    }
}
