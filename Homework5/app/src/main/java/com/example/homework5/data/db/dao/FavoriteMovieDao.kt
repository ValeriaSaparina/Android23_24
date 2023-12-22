package com.example.homework5.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homework5.data.db.entities.FavoriteMovieEntity
import com.example.homework5.data.models.MovieModel

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity)

    @Query("SELECT * FROM movie INNER JOIN favorite_movies ON movie.movie_id = favorite_movies.movie_id WHERE favorite_movies.user_id = :userId")
    fun getFavoriteMoviesForUser(userId: Long): List<MovieModel>?

    @Delete
    fun deleteFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity)

}