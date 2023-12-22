package com.example.homework5.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homework5.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var viewBinding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMainBinding.inflate(inflater)
        return viewBinding?.root
    }

    companion object {
        const val FRAGMENT_TAG = "main fragment"

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }


}
