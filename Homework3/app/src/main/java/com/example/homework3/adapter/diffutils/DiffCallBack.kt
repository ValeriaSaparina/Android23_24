package com.example.homework3.adapter.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.homework3.models.NewsData

class DiffCallBack(
    private val oldList: List<Any?>,
    private val newList: List<Any?>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition] as? NewsData
        val new = newList[newItemPosition] as? NewsData
        return old?.id == new?.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition] as? NewsData
        val new = newList[newItemPosition] as? NewsData
        return old?.title == new?.title &&
                old?.description == new?.description
    }
}