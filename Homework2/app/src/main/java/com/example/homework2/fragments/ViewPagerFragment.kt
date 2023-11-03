package com.example.homework2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.homework2.BaseFragment
import com.example.homework2.Helper
import com.example.homework2.R
import com.example.homework2.adapters.FragmentAdapter
import com.example.homework2.databinding.FragmentViewPagerBinding

class ViewPagerFragment(private val questionCount: Int) :
    BaseFragment(R.layout.fragment_view_pager) {

    private var viewBinding: FragmentViewPagerBinding? = null

    private var vpAdapter: FragmentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentViewPagerBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repeat(questionCount) { Helper.answerCheckedMutableList.add(-1) }

        vpAdapter = FragmentAdapter(manager = parentFragmentManager, lifecycle, questionCount)
        viewBinding?.fragmentVp?.adapter = vpAdapter

        viewBinding?.apply {
            fragmentVp.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                var currentState: Int = 0
                var currentPos: Int = 0

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    if (currentState == ViewPager2.SCROLL_STATE_DRAGGING && positionOffsetPixels == 0) {
                        if (currentPos == 0) {
                            fragmentVp.currentItem = questionCount - 1
                        } else if (currentPos == questionCount - 1) {
                            fragmentVp.currentItem = 0
                        }
                    }
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    currentPos = position
                    super.onPageSelected(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    currentState = state
                    super.onPageScrollStateChanged(state)
                }
            })
        }

    }
}