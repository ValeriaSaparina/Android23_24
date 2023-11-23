package com.example.homework3.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework3.BaseFragment
import com.example.homework3.R
import com.example.homework3.adapter.NewsFeedAdapter
import com.example.homework3.databinding.FragmentNewsFeedBinding
import com.example.homework3.databinding.ItemNewsfeedBinding
import com.example.homework3.models.NewsData
import com.example.homework3.utils.NewsGenerator

class NewsFeedFragment : BaseFragment(R.layout.fragment_news_feed) {

    private var viewBinding: FragmentNewsFeedBinding? = null
    private var numberOfNews = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewsFeedBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.apply {
            numberOfNews = getInt("NumberOfNews")

        }

        initRecyclerView()

    }

    private fun initRecyclerView() {
        viewBinding?.apply {

            newsFeedRv.apply {

                val mAdapter = NewsFeedAdapter(
                    count = numberOfNews,
                    onItemClicked = ::onItemClicked,
                    onLikeButtonClicked = ::onLikeButtonClicked,
                    onButtonClicked = ::onButtonClicked,

                    )

                val mLayoutManager = if (numberOfNews <= 12) LinearLayoutManager(
                    requireContext(),
                    RecyclerView.VERTICAL,
                    false
                ) else {
                    GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false).apply {
                        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int {
                                return when (mAdapter.getItemViewType(position)) {
                                    2 -> 1
                                    else -> 2
                                }
                            }
                        }
                    }
                }
                layoutManager = mLayoutManager
                adapter = mAdapter
            }
        }
    }

    private fun onItemClicked(newsItem: NewsData) {
        parentFragmentManager.beginTransaction()
            .replace(
                fragmentContainerId,
                NewsDetailsFragment.newInstance(newsItem),
                NewsDetailsFragment.NEWS_DETAIL_FRAGMENT_TAG
            )
            .addToBackStack(null)
            .commit()
    }

    private fun onLikeButtonClicked(
        position: Int,
        viewBinding: ItemNewsfeedBinding
    ) {
        val elem = NewsGenerator.getNewsPos(position)
        elem.isFav = !elem.isFav
        if (elem.isFav) viewBinding.favIv.setImageResource(R.drawable.ic_favorite_40)
        else viewBinding.favIv.setImageResource(R.drawable.ic_not_favorite_40)

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onButtonClicked() {
        val dialog = BottomSheetFragment.newInstance { oldList, newList ->
            viewBinding?.apply {
                (newsFeedRv.adapter as NewsFeedAdapter).updateItems(oldList, newList)
            }
        }
        dialog.show(childFragmentManager, BottomSheetFragment.BOTTOM_SHEET_FRAGMENT_TAG)
    }


    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }


    companion object {
        fun newInstance(numberOfNews: Int): NewsFeedFragment {
            return NewsFeedFragment().apply {
                arguments = Bundle().apply {
                    putInt("NumberOfNews", numberOfNews)
                }
            }
        }
    }

}