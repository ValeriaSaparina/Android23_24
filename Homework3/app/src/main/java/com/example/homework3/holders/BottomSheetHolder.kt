package com.example.homework3.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.homework3.databinding.ItemButtonBinding

class BottomSheetHolder(
    viewBinding: ItemButtonBinding,
    private val onButtonClicked: () -> Unit,
) : RecyclerView.ViewHolder(viewBinding.root) {

    init {
        viewBinding.bottomSheetBtn.setOnClickListener { onButtonClicked() }
    }

}