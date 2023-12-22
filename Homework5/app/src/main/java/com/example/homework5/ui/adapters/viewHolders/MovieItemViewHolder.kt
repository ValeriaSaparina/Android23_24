package com.example.homework5.ui.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.homework5.databinding.ItemMovieBinding
import com.example.homework5.ui.items.MovieItem

class MovieItemViewHolder(
    private val viewBinding: ItemMovieBinding,
    private val onButtonClicked: (MovieItem) -> Unit,
    private val onItemClicked: (MovieItem) -> Unit
) :
    RecyclerView.ViewHolder(viewBinding.root) {

    private var movieItem: MovieItem? = null

    fun bind(movieItem: MovieItem) {
        this.movieItem = movieItem

        viewBinding.apply {
            changeTextAddFavBtn()
            titleTv.text = movieItem.name
            descTv.text = movieItem.description

            addToFavoritesBtn.setOnClickListener {
                onButtonClicked(movieItem)
                changeTextAddFavBtn()
            }
            root.setOnClickListener {
                onItemClicked(movieItem)
            }
        }
    }

    private fun changeTextAddFavBtn() {
        if (movieItem?.isFav == true) {
            viewBinding.addToFavoritesBtn.text = "Remove from favorites movies"
        } else {
            viewBinding.addToFavoritesBtn.text = "Add to favorites movies"
        }
    }

    fun getMovie() = movieItem
}