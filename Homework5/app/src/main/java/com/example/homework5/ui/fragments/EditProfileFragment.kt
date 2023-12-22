package com.example.homework5.ui.fragments

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.homework5.helpers.CurrentUser
import com.example.homework5.helpers.Helper
import com.example.homework5.helpers.Params
import com.example.homework5.databinding.FragimentEditProfileBinding
import com.example.homework5.helpers.ServiceLocator
import com.example.homework5.ui.watchers.PhoneTextWatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class EditProfileFragment : Fragment() {

    private var viewBinding: FragimentEditProfileBinding? = null
    private val dbInstance = ServiceLocator.getDbInstance()
    val user = CurrentUser.getCurrentUser()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragimentEditProfileBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserData()
        initListener(view)
    }

    private fun setUserData() {
        viewBinding?.apply {
            phoneEt.setText(user.phone)
        }
    }

    private fun initListener(view: View) {
        viewBinding?.apply {
            saveBtn.setOnClickListener {

                lifecycleScope.launch {
                    try {
                        setUpdatedUserModel()
                        parentFragmentManager.popBackStack()
                    } catch (e: SQLiteConstraintException) {
                        Helper.showNotification(context, Params.ERROR_UNIQUE_PHONE)
                    }
                }

            }

            phoneEt.apply {
                addTextChangedListener(PhoneTextWatcher(view))

                setOnClickListener {
                    val length = this.text?.length ?: 0
                    if (length == 0) {
                        this.setText("+7 (")
                    }
                }
            }

            deleteProfileBtn.setOnClickListener {
                lifecycleScope.launch {
//                    withContext(Dispatchers.IO) {
//                        dbInstance.userDao.deleteUser(UserMapping.toEntity(user))
//                    }
                    withContext(Dispatchers.IO) {
                        dbInstance.userDao. updateUserDeletionTime(Date(), user.userId)
                    }
                }
                val pref = ServiceLocator.getSharedPreferencesInstance()
                pref.edit().remove(Params.AUTH_SESSION).apply()
                parentFragmentManager.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
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

    private suspend fun setUpdatedUserModel() {
        val currentUser = CurrentUser.getCurrentUser()

        val prevPhone = currentUser.phone
        var phone = ""
        var password = ""

        viewBinding?.apply {
            password = passwordEt.text.toString()
            phone = phoneEt.text.toString()
        }
        val hashedPassword = if (password.isEmpty()) "" else Helper.hashedPassword(password)

        if (phone.isEmpty() && hashedPassword.isEmpty()) {
            return
        }

        if (phone.isEmpty() && hashedPassword.isNotEmpty()) {
            currentUser.password = hashedPassword
            updateUserPassword(hashedPassword, currentUser.userId)
            return
        }

        if (phone.isNotEmpty() && hashedPassword.isEmpty()) {
            if (prevPhone != phone) {
                updateUserPhone(phone, currentUser.userId)
            }
            return
        }

        if (phone.isNotEmpty() && hashedPassword.isNotEmpty()) {
            if (prevPhone != phone) {
                updateUserData(phone, hashedPassword, currentUser.userId)
            } else {
                updateUserPassword(hashedPassword, currentUser.userId)
            }
        }
    }

    private suspend fun updateUserPhone(phone: String, userId: Long) {
        val currentUser = CurrentUser.getCurrentUser()
        withContext(Dispatchers.IO) {
            dbInstance.userDao.updateUserPhoneQuery(
                phone,
                userId
            )
        }
        currentUser.phone = phone
    }

    private suspend fun updateUserPassword(hashedPassword: String, userId: Long) {
        val currentUser = CurrentUser.getCurrentUser()
        withContext(Dispatchers.IO) {
            dbInstance.userDao.updateUserPasswordQuery(
                hashedPassword,
                userId
            )
        }
        currentUser.password = hashedPassword
    }

    private suspend fun updateUserData(phone: String, password: String, userId: Long) {
        val currentUser = CurrentUser.getCurrentUser()
        withContext(Dispatchers.IO) {
            dbInstance.userDao.updateUserPhonePasswordQuery(
                phone,
                password,
                userId
            )
        }
        currentUser.phone = phone
        currentUser.password = password
    }

    companion object {
        const val FRAGMENT_TAG = "edit profile fragment"

        fun newInstance(): EditProfileFragment {
            return EditProfileFragment()
        }
    }

}
