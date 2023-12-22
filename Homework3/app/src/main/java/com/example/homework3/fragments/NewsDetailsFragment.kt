package com.example.homework3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.homework3.BaseFragment
import com.example.homework3.R
import com.example.homework3.databinding.FragmentNewsDetailsBinding
import com.example.homework3.models.NewsData

class NewsDetailsFragment : BaseFragment(R.layout.fragment_news_details) {

    private var viewBinding: FragmentNewsDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewsDetailsBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.apply {
            arguments?.let { args ->
                imageImg.setImageResource(args.getInt("img"))
                headerTv.text = args.getString("title")
                bodyTv.text = args.getString("description")
            }
        }
    }

    companion object {
        const val NEWS_DETAIL_FRAGMENT_TAG = "NEWS_DETAIL_FRAGMENT_TAG"

        fun newInstance(newsItem: NewsData): NewsDetailsFragment {
            return NewsDetailsFragment().apply {
                arguments = bundleOf(
                    "title" to newsItem.title,
                    "description" to newsItem.description,
                    "img" to newsItem.img,
                    "isFav" to newsItem.isFav
                )
            }
        }

    }
}
