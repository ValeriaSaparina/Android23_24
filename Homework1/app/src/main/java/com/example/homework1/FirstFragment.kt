package com.example.homework1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homework1.Base.BaseFragment
import com.example.homework1.databinding.FragmentFirstBinding

class FirstFragment : BaseFragment() {

    private var viewBinding: FragmentFirstBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentFirstBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.apply {
            firstClickMeBtn.setOnClickListener {

                parentFragmentManager.beginTransaction()

                    .replace(
                        firstFragmentContainerId,
                        SecondFragment.newInstance(
                            toMove = true,
                            message = this.inputEt.text.toString()
                        ),
                        SecondFragment.SECOND_FRAGMENT_TAG
                    )
                    .addToBackStack(null)
                    .commit()
            }

            saveBtn?.setOnClickListener {
                val text = this.inputEt.text.toString()
                if (text != "") {
                    Helper.add(text)
                    val fourthFragment =
                        parentFragmentManager.findFragmentByTag(FourthFragment.FOURTH_FRAGMENT_TAG) as FourthFragment
                    fourthFragment.update()
                    this.inputEt.text = null
                }
            }
        }

    }

    companion object {
        const val FIRST_FRAGMENT_TAG = "FIRST_FRAGMENT_TAG"
        fun newInstance(): FirstFragment {
            return FirstFragment()
        }
    }
}