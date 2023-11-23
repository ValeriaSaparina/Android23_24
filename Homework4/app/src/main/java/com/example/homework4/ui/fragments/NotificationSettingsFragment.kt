package com.example.homework4.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homework4.Settings
import com.example.homework4.databinding.FragmentNotificationSettingsBinding

class NotificationSettingsFragment : Fragment() {

    private var viewBinding: FragmentNotificationSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNotificationSettingsBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveSettings()
    }

    private fun saveSettings() {
        viewBinding?.apply {
            importanceGroup.setOnCheckedChangeListener { _, i ->
                Settings.setImportance(view?.findViewById(i) ?: mediumRb)
            }
            visibilityGroup.setOnCheckedChangeListener { _, i ->
                Settings.setVisibility(view?.findViewById(i) ?: mediumRb)
            }
            showFullMessageChb.setOnCheckedChangeListener { _, _ ->
                Settings.setIsShowFullMessage(showFullMessageChb.isChecked)
            }
            twoButtonsChb.setOnCheckedChangeListener { _, _ ->
                Settings.setIsShowTwoButtons(twoButtonsChb.isChecked)
            }
        }
    }
}