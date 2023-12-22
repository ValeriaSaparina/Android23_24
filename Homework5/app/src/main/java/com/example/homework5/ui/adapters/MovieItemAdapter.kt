package com.example.homework5.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homework5.databinding.ItemMovieBinding
import com.example.homework5.ui.adapters.diffutils.DiffCallbackMovies
import com.example.homework5.ui.adapters.viewHolders.MovieItemViewHolder
import com.example.homework5.ui.items.MovieItem

class MovieItemAdapter(
    private var allMoviesList: List<MovieItem>,
    private val ctx: Context,
    private val onButtonClicked: (MovieItem) -> Unit,
    private val onItemClicked: (MovieItem) -> Unit
) : RecyclerView.Adapter<MovieItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val viewBinding = ItemMovieBinding.inflate(LayoutInflater.from(ctx), parent, false)
        return MovieItemViewHolder(
            viewBinding = viewBinding,
            onButtonClicked = onButtonClicked,
            onItemClicked = onItemClicked
        )
    }

    override fun getItemCount(): Int = allMoviesList.size

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val movieItem = allMoviesList[position]
        holder.bind(movieItem)
    }

    fun updateItems(newList: List<MovieItem>) {
        val diff = DiffCallbackMovies(allMoviesList, newList)
        val diffResult = DiffUtil.calculateDiff(diff)
        allMoviesList = newList
        diffResult.dispatchUpdatesTo(this)
    }

}