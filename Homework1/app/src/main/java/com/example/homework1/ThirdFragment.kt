package com.example.homework1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homework1.Base.BaseFragment
import com.example.homework1.databinding.ThirdFragmentBinding

class ThirdFragment : BaseFragment() {

    private var viewBinding: ThirdFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = ThirdFragmentBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val title = it.getString(ParamKeys.MESSAGE)
            if (title?.equals("") == false)
                viewBinding?.inputEt?.text = title

        }
    }


    companion object {
        const val THIRD_FRAGMENT_TAG = "THIRD_FRAGMENT_TAG"
        fun newInstance(message: String?): Fragment {
            return ThirdFragment().apply {
                arguments = Bundle().apply {
                    putString(ParamKeys.MESSAGE, message)
                }
            }
        }
    }
}