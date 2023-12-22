package com.example.homework5.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.homework5.data.db.dao.FavoriteMovieDao
import com.example.homework5.data.db.dao.MovieDao
import com.example.homework5.data.db.dao.RatedMoviesDao
import com.example.homework5.data.db.dao.UserDao
import com.example.homework5.data.db.entities.FavoriteMovieEntity
import com.example.homework5.data.db.entities.MovieEntity
import com.example.homework5.data.db.entities.RatedMoviesEntity
import com.example.homework5.data.db.entities.UserEntity
import com.example.homework5.data.db.typeConverters.DateConverter

@Database(
    entities = [UserEntity::class, MovieEntity::class, FavoriteMovieEntity::class, RatedMoviesEntity::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class HwDatabase : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val movieDao: MovieDao
    abstract val favoriteMovieDao: FavoriteMovieDao
    abstract val ratedMoviesDao: RatedMoviesDao

}