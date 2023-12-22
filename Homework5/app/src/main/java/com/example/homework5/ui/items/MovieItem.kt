package com.example.homework5.ui.items


data class MovieItem (
    val id: Long,
    val name: String = "title",
    val releaseDate: Int = 2000,
    val description: String = "desc",
    val rating: Double = 0.0,
    var isFav: Boolean = true,
    var userRating: Int = 0
)