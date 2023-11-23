package com.example.homework1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homework1.Base.BaseFragment
import com.example.homework1.databinding.FragmentSecondBinding

class SecondFragment : BaseFragment() {


    private var viewBinding: FragmentSecondBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSecondBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.getBoolean(ParamKeys.TO_MOVE)) {
                parentFragmentManager.beginTransaction()
                    .replace(
                        firstFragmentContainerId,
                        ThirdFragment.newInstance(message = it.getString(ParamKeys.MESSAGE)),
                        ThirdFragment.THIRD_FRAGMENT_TAG
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }

        viewBinding?.apply {
            val title = arguments?.getString(ParamKeys.MESSAGE)
            if (title?.equals("") == false)
                inputEt.text = title

            firstClickMeBtn.setOnClickListener {
                parentFragmentManager.popBackStack()
                parentFragmentManager.beginTransaction()
                    .replace(
                        firstFragmentContainerId,
                        ThirdFragment.newInstance(message = title),
                        ThirdFragment.THIRD_FRAGMENT_TAG
                    )
                    .addToBackStack(null)
                    .commit()
            }
            secondClickMeBtn.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

        }

    }

    override fun onStop() {
        super.onStop()
        arguments?.let {
            it.remove(ParamKeys.TO_MOVE)
        }
    }

    companion object {
        const val SECOND_FRAGMENT_TAG = "SECOND_FRAGMENT_TAG"
        fun newInstance(toMove: Boolean = false, message: String): Fragment {
            return SecondFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ParamKeys.TO_MOVE, toMove)
                    putString(ParamKeys.MESSAGE, message)
                }
            }
        }
    }
}