package com.example.homework5.data.models

import androidx.room.ColumnInfo

data class MovieModel(
    @ColumnInfo(name = "movie_id") var movieId: Long,
    var name: String,
    var description: String,
    var rating: Double,
    @ColumnInfo(name = "release_date") var releaseDate: Int

)