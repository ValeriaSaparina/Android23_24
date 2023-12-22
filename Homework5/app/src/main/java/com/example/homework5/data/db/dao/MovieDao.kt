package com.example.homework5.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homework5.data.db.entities.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getAllMovies(): List<MovieEntity>?

    @Query("SELECT * FROM movie WHERE movie_id = :movieId")
    fun getMovieById(movieId: Long): MovieEntity?

    @Query("SELECT * FROM movie WHERE name = :name and release_date = :releaseDate")
    fun getMovieByNameAndReleaseDate(name: String, releaseDate: Int) : MovieEntity?

    @Delete
    fun deleteMovie(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie ORDER BY release_date DESC")
    fun getMoviesByReleaseYearDesc(): List<MovieEntity>

    @Query("SELECT * FROM movie ORDER BY release_date ASC")
    fun getMoviesByReleaseYearAsc(): List<MovieEntity>

    @Query("SELECT * FROM movie ORDER BY rating ASC")
    fun getMoviesByRatingAsc(): List<MovieEntity>

    @Query("SELECT * FROM movie ORDER BY rating DESC")
    fun getMoviesByRatingDesc(): List<MovieEntity>

    @Query("UPDATE movie set rating = (SELECT AVG(rating) FROM rated_movies WHERE movie_id = :movieId and rating <> 0) WHERE movie_id = :movieId")
    fun updateAvgRating(movieId: Long)

}