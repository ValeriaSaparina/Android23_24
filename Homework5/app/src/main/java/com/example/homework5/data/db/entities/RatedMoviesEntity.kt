package com.example.homework5.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "rated_movies",
    primaryKeys=["user_id", "movie_id"],
    foreignKeys = [
        ForeignKey(entity = UserEntity::class, parentColumns = ["user_id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = MovieEntity::class, parentColumns = ["movie_id"], childColumns = ["movie_id"], onDelete = ForeignKey.CASCADE)
    ])
data class RatedMoviesEntity(
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "movie_id") val movieId: Long,
    val rating: Int
)