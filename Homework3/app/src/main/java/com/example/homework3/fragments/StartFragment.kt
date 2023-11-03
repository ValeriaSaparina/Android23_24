package com.example.homework3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.homework3.BaseFragment
import com.example.homework3.R
import com.example.homework3.databinding.FragmentStartBinding
import com.example.homework3.utils.Helper
import com.example.homework3.utils.NewsGenerator

class StartFragment : BaseFragment(R.layout.fragment_start) {

    private var viewBinding: FragmentStartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentStartBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NewsGenerator.removeAll()
        initListener()
        viewBinding?.apply {
            try {
                enterBtn.isEnabled = numberEt.text.toString().toInt() > Helper.maxNews
            } catch (ex: NumberFormatException) {
                enterBtn.isEnabled = true
            }
        }


    }

    private fun initListener() {
        viewBinding?.apply {

            numberEt.addTextChangedListener {
                Helper.textChangedListener(numberEt, Helper.maxNews)
            }

            enterBtn.setOnClickListener {
                val text = numberEt.text
                val number: Int = if (!text.isNullOrEmpty()) text.toString().toInt() else 0
                parentFragmentManager.beginTransaction()
                    .replace(
                        fragmentContainerId,
                        NewsFeedFragment.newInstance(number)
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    companion object {
        fun newInstance(): StartFragment {
            return StartFragment()
        }
    }

}