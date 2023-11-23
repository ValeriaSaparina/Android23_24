package com.example.homework3.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.homework3.databinding.ItemDateSeparatorBinding
import com.example.homework3.models.DateData

class DateSeparatedHolder(private val viewBinding: ItemDateSeparatorBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bindItem(item: DateData) {
        viewBinding.dateTv.text = item.date
    }

}