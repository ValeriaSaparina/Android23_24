package com.example.homework5.helpers

enum class FilterTypes(
    private val value: Int
) {
    YEAR_ASC(0),
    YEAR_DESC(1),
    RATING_ASC(2),
    RATING_DESC(3);

    fun getValue() = value
}