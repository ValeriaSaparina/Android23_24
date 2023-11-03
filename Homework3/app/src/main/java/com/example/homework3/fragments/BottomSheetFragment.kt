package com.example.homework3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.homework3.R
import com.example.homework3.databinding.ItemBottomSheetBinding
import com.example.homework3.utils.Helper
import com.example.homework3.utils.NewsGenerator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment(private val notify: (oldList: MutableList<Any>, newList: MutableList<Any>) -> Unit) :
    BottomSheetDialogFragment(R.layout.item_bottom_sheet) {

    private var viewBinding: ItemBottomSheetBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = ItemBottomSheetBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()

    }

    private fun initListeners() {
        viewBinding?.apply {
            addBtn.setOnClickListener {
                val countExtraNews = Integer.parseInt(this.extraNewsEt.text.toString())
                if (countExtraNews <= Helper.maxExtraNews) {
                    val oldList: MutableList<Any> = mutableListOf()
                    oldList.addAll(NewsGenerator.getNews(-1))
                    NewsGenerator.addNewNews(countExtraNews)
                    notify(
                        oldList,
                        NewsGenerator.getNews(-1)
                    )
                    dismiss()
                }
            }
            extraNewsEt.addTextChangedListener {
                Helper.textChangedListener(
                    extraNewsEt,
                    Helper.maxExtraNews
                )
            }
        }
    }


    companion object {
        const val BOTTOM_SHEET_FRAGMENT_TAG = "BOTTOM_SHEET_FRAGMENT_TAG"

        fun newInstance(notify: (oldList: MutableList<Any>, newList: MutableList<Any>) -> Unit): BottomSheetFragment {
            return BottomSheetFragment(notify)
        }
    }

}