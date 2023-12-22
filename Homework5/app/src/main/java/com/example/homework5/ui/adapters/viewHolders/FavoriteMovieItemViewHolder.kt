package com.example.homework5.ui.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.homework5.databinding.ItemFavoriteMovieBinding
import com.example.homework5.ui.items.MovieItem

class FavoriteMovieItemViewHolder(
    private val viewBinding: ItemFavoriteMovieBinding,
    private val onButtonClicked: (MovieItem) -> Unit,
    private val onItemClicked: (MovieItem) -> Unit
) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(favoriteMovieItem: MovieItem) {

        viewBinding.apply {
            titleTv.text = favoriteMovieItem.name
            descTv.text = favoriteMovieItem.description

            removeFromFavoritesBtn.setOnClickListener {
                onButtonClicked(favoriteMovieItem)
            }
            root.setOnClickListener {
                onItemClicked(favoriteMovieItem)
            }
        }
    }
}
