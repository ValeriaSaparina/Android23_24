package com.example.homework3.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.homework3.R
import com.example.homework3.databinding.ItemNewsfeedBinding
import com.example.homework3.models.NewsData

class NewsFeedHolder(
    private val viewBinding: ItemNewsfeedBinding,
    private val onItemClicked: (NewsData) -> Unit,
    private val onLikeButtonClicked: (Int, ItemNewsfeedBinding) -> Unit,
) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bindItem(position: Int, data: NewsData) {
        viewBinding.apply {
            titleTv.text = data.title
            newsIv.setImageResource(data.img)
            if (data.isFav) favIv.setImageResource(R.drawable.ic_favorite_40)
            else favIv.setImageResource(R.drawable.ic_not_favorite_40)

            root.setOnClickListener {
                onItemClicked(data)
            }

            favIv.setOnClickListener {
                onLikeButtonClicked(position, this)
            }

        }
    }
}