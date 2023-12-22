package com.example.homework5.ui.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.homework5.ui.items.MovieItem

class DiffCallbackMovies(
    private val oldList: List<MovieItem>,
    private val newList: List<MovieItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie = oldList[oldItemPosition]
        val newMovie = newList[newItemPosition]
        return oldMovie.id == newMovie.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie = oldList[oldItemPosition]
        val newMovie = newList[newItemPosition]
        return oldMovie.name == newMovie.name && oldMovie.releaseDate == newMovie.releaseDate && oldMovie.description == newMovie.description && oldMovie.isFav == newMovie.isFav
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return if (!areContentsTheSame(oldItemPosition, newItemPosition)) {
            newList[newItemPosition]
        } else {
            super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }

}