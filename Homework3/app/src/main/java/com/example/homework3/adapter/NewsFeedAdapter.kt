package com.example.homework3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homework3.databinding.ItemButtonBinding
import com.example.homework3.databinding.ItemDateSeparatorBinding
import com.example.homework3.databinding.ItemNewsfeedBinding
import com.example.homework3.holders.BottomSheetHolder
import com.example.homework3.holders.DateSeparatedHolder
import com.example.homework3.holders.NewsFeedHolder
import com.example.homework3.models.DateData
import com.example.homework3.models.NewsData
import com.example.homework3.utils.NewsGenerator
import com.example.homework3.adapter.diffutils.DiffCallBack

class NewsFeedAdapter(
    count: Int,
    private val onItemClicked: (NewsData) -> Unit,
    private val onButtonClicked: () -> Unit,
    private val onLikeButtonClicked: (Int, ItemNewsfeedBinding) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val news = NewsGenerator.getNews(count)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            2 -> {
                NewsFeedHolder(
                    viewBinding = ItemNewsfeedBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onItemClicked = onItemClicked,
                    onLikeButtonClicked = onLikeButtonClicked
                )
            }

            0 -> {
                BottomSheetHolder(
                    viewBinding = ItemButtonBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onButtonClicked = onButtonClicked
                )
            }

            else -> {
                DateSeparatedHolder(
                    viewBinding = ItemDateSeparatorBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 0
        }

        if (position % 9 == 0) {
            return 1
        }
        return 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BottomSheetHolder -> {}
            is DateSeparatedHolder -> {
                holder.bindItem(news[position] as DateData)
            }

            is NewsFeedHolder -> {
                holder.bindItem(position, news[position] as NewsData)
            }
        }
    }

    fun updateItems(oldList: List<Any?>, newList: List<Any?>) {
        val diff = DiffCallBack(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diff)
        diffResult.dispatchUpdatesTo(this)
    }

}
