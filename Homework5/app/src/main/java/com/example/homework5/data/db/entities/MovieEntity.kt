package com.example.homework5.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "movie_id") var movieId: Long,
    val name: String,
    val description: String,
    val rating: Double,
    @ColumnInfo(name = "release_date") var releaseDate: Int
)
