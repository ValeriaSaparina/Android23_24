package com.example.homework5.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.homework5.helpers.CurrentUser
import com.example.homework5.helpers.Params
import com.example.homework5.databinding.FragmentProfileBinding
import com.example.homework5.helpers.ServiceLocator

class ProfileFragment : Fragment() {

    private var viewBinding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentProfileBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUserData()
        initListeners()

    }

    private fun initListeners() {
        viewBinding?.apply {
            editBtn.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        Params.containerViewId,
                        EditProfileFragment.newInstance(),
                        EditProfileFragment.FRAGMENT_TAG
                    )
                    .addToBackStack(null)
                    .commit()
            }

            logOutBtn.setOnClickListener {

                val pref = ServiceLocator.getSharedPreferencesInstance()
                pref.edit().remove(Params.AUTH_SESSION).apply()

                parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                parentFragmentManager.beginTransaction()
                    .replace(
                        Params.containerViewId,
                        AuthFragment.newInstance(),
                        AuthFragment.FRAGMENT_TAG
                    )
                    .commit()
            }
        }
    }

    private fun setUserData() {
        val user = CurrentUser.getCurrentUser()
        viewBinding?.apply {
            nameTv.text = user.name
            emailTv.text = user.email
            phoneTv.text = user.phone
        }
    }

    companion object {
        const val FRAGMENT_TAG = "profile fragment"

        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }

    }

}