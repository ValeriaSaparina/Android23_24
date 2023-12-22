package com.example.homework5.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homework5.data.db.entities.RatedMoviesEntity
import com.example.homework5.data.models.MovieModel


@Dao
interface RatedMoviesDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addRate(ratedMoviesEntity: RatedMoviesEntity)

    @Query("SELECT * FROM movie INNER JOIN rated_movies ON movie.movie_id = rated_movies.movie_id WHERE rated_movies.user_id = :userId")
    fun getRatedMoviesForUser(userId: Long): List<MovieModel>?

    @Delete
    fun deleteRatedMovie(ratedMoviesEntity: RatedMoviesEntity)

    @Query("UPDATE rated_movies SET rating = :rating WHERE user_id = :userId and movie_id = :movieId")
    fun updateRate(rating: Int, userId: Long, movieId: Long)

}