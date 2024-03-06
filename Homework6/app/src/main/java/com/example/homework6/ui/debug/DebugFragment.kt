package com.example.homework6.ui.debug

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.homework6.databinding.FragmentDebugBinding
import com.example.homework6.ui.base.BaseFragment
import com.example.homework6.ui.debug.model.DebugDataUiModel

class DebugFragment : BaseFragment() {

    private var viewBinding: FragmentDebugBinding? = null
    private val viewModel: DebugViewModel by viewModels {
        DebugViewModel.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentDebugBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with (viewModel) {

            getDebugData()

            debugData.observe {
                if (it != null) {
                    showData(it)
                }
            }
        }

    }

    private fun showData(it: DebugDataUiModel) {
        viewBinding?.run {
            appNameTv.text = it.appNameDebug
            androidVersionTv.text = it.androidVersionDebug
            appVersionTv.text = it.appVersionDebug
            baseUrlTv.text = it.baseUrlDebug
            deviceModelTv.text = it.deviceModelDebug
        }
    }

}
