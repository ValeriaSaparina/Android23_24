package com.example.homework5.data.mappings

import com.example.homework5.data.db.entities.MovieEntity
import com.example.homework5.data.models.MovieModel
import com.example.homework5.ui.items.MovieItem

object MovieMapping {


    fun toEntity(movieModel: MovieModel): MovieEntity {
        return MovieEntity(
            movieId = movieModel.movieId,
            name = movieModel.name,
            description = movieModel.description,
            rating = movieModel.rating,
            releaseDate = movieModel.releaseDate
        )
    }

    fun toModel(movieEntity: MovieEntity): MovieModel {
        return MovieModel(
            movieId = movieEntity.movieId,
            name = movieEntity.name,
            description = movieEntity.description,
            rating = movieEntity.rating,
            releaseDate = movieEntity.releaseDate
        )
    }

    fun toEntity(movieItem: MovieItem): MovieEntity {
        return MovieEntity(
            movieId = movieItem.id,
            name = movieItem.name,
            description = movieItem.description,
            rating = movieItem.rating,
            releaseDate = movieItem.releaseDate
        )
    }

    fun toItem(movieEntity: MovieEntity, favList: List<MovieModel>): MovieItem {
        return MovieItem(
            id = movieEntity.movieId,
            name = movieEntity.name,
            description = movieEntity.description,
            rating = movieEntity.rating,
            releaseDate = movieEntity.releaseDate,
            isFav = favList.contains(toModel(movieEntity))
        )
    }

    fun toItem(movieModel: MovieModel): MovieItem {
        return MovieItem(
            id = movieModel.movieId,
            name = movieModel.name,
            description = movieModel.description,
            rating = movieModel.rating,
            releaseDate = movieModel.releaseDate,
        )
    }
}