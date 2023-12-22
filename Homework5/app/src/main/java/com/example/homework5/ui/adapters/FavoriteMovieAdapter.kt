package com.example.homework5.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homework5.databinding.ItemFavoriteMovieBinding
import com.example.homework5.ui.adapters.diffutils.DiffCallbackFavorites
import com.example.homework5.ui.adapters.viewHolders.FavoriteMovieItemViewHolder
import com.example.homework5.ui.items.MovieItem

class FavoriteMovieAdapter(
    private var favoriteMoviesList: List<MovieItem>,
    private val ctx: Context,
    private val onButtonClicked: (MovieItem) -> Unit,
    private val onItemClicked: (MovieItem) -> Unit
) : RecyclerView.Adapter<FavoriteMovieItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieItemViewHolder {
        val viewBinding = ItemFavoriteMovieBinding.inflate(LayoutInflater.from(ctx), parent, false)
        return FavoriteMovieItemViewHolder(
            viewBinding = viewBinding,
            onButtonClicked = onButtonClicked,
            onItemClicked = onItemClicked
        )
    }

    override fun getItemCount(): Int = favoriteMoviesList.size

    override fun onBindViewHolder(holder: FavoriteMovieItemViewHolder, position: Int) {
        val favMovieItem = favoriteMoviesList[position]
        holder.bind(favMovieItem)
    }

    fun updateItems(newList: List<MovieItem>) {
        val diff = DiffCallbackFavorites(favoriteMoviesList, newList)
        val diffResult = DiffUtil.calculateDiff(diff)
        favoriteMoviesList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}