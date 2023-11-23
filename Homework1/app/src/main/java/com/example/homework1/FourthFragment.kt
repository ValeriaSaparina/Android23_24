package com.example.homework1

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homework1.Base.BaseFragment
import com.example.homework1.databinding.FragmentFourthBinding

class FourthFragment : BaseFragment() {

    private var viewBinding: FragmentFourthBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewBinding = FragmentFourthBinding.inflate(inflater)
            viewBinding?.root
        } else super.onCreateView(inflater, container, savedInstanceState)
    }



    fun update() {
        viewBinding?.apply {
            when (Helper.getPosition()) {
                1 -> firstTv.text = Helper.getValue()
                2 -> secondTv.text = Helper.getValue()
                3 -> thirdTv.text = Helper.getValue()
            }
        }
    }

    companion object {
        const val FOURTH_FRAGMENT_TAG = "FOURTH_FRAGMENT_TAG"

        fun newInstance(): Fragment {
            return FourthFragment()
        }


    }


}
