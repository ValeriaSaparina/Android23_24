package com.example.homework2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homework2.BaseFragment
import com.example.homework2.R
import com.example.homework2.databinding.FragmentStartBinding
import com.example.homework2.watchers.FillTextWatcher
import com.example.homework2.watchers.PhoneTextWatcher

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
        initListeners()
        viewBinding?.clickMeBtn?.isClickable = false
    }


    private fun initListeners() {
        viewBinding?.apply {
            phoneEt.apply {
                addTextChangedListener(PhoneTextWatcher(viewBinding))

                setOnClickListener {
                    Log.d("DEBUG", "setOnClickListener phoneEt")
                    val length = this.text?.length ?: 0
                    if (length == 0) {
                        this.setText("+7 (")
                    }
                }
            }

            inputEt.apply {
                addTextChangedListener(FillTextWatcher(viewBinding))
            }

            clickMeBtn.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.container_view,
                        ViewPagerFragment(inputEt.text.toString().toInt())
                    )
                    .commit()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    companion object {
        const val START_FRAGMENT_TAG: String = "START_FRAGMENT_TAG"

        fun newInstance(): StartFragment {
            return StartFragment()
        }
    }


}