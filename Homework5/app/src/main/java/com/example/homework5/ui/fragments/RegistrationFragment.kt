package com.example.homework5.ui.fragments

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.homework5.helpers.Helper
import com.example.homework5.helpers.Params
import com.example.homework5.databinding.FragmentRegistrationBinding
import com.example.homework5.helpers.ServiceLocator
import com.example.homework5.exceptions.InvalidInputData
import com.example.homework5.data.mappings.UserMapping
import com.example.homework5.data.models.UserModel
import com.example.homework5.ui.watchers.PhoneTextWatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest

class RegistrationFragment : Fragment() {

    private var viewBinding: FragmentRegistrationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentRegistrationBinding.inflate(inflater)
        return viewBinding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners(view)
    }

    private fun initListeners(view: View) {

        viewBinding?.apply {
            val dbInstance = ServiceLocator.getDbInstance()

            phoneEt.apply {
                addTextChangedListener(PhoneTextWatcher(view))

                setOnClickListener {
                    val length = this.text?.length ?: 0
                    if (length == 0) {
                        this.setText("+7 (")
                    }
                }
            }

            regBtn.setOnClickListener {
                try {
                    val user = getUser()
                    user?.let {
                        lifecycleScope.launch {
                            try {
                                withContext(Dispatchers.IO) {
                                    dbInstance.userDao.addUser(UserMapping.toEntity(it))
                                }
                                parentFragmentManager.popBackStack()
                            } catch (e: SQLiteConstraintException) {
                                Toast.makeText(
                                    context,
                                    Params.ERROR_UNIQUE_PHONE,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                } catch (e: NullPointerException) {
                    Helper.showNotification(context, Params.ERROR_EMPTY_FIELDS)
                } catch (e: InvalidInputData) {
                    Helper.showNotification(context, e.message)
                }
            }
        }
    }

    private fun getUser(): UserModel? {
        viewBinding?.apply {
            val phone = phoneEt.text.toString()
            val email = emailEt.text.toString()
            val name = nameEt.text.toString()
            val password = passwordEt.text.toString()

            Helper.isValidEmail(email)

            if (phone.isEmpty() || email.isEmpty() || name.isEmpty() || password.isEmpty()) {
                throw NullPointerException()
            }
            val md = MessageDigest.getInstance(Params.ALGORITHM)
            val pass = md.digest(passwordEt.text.toString().toByteArray(Charsets.UTF_8))
                .joinToString("")
            return UserModel(
                userId = 0,
                phone = phone,
                email = email,
                name = name,
                password = pass
            )
        }
        return null
    }

    companion object {

        const val FRAGMENT_TAG = "registration fragment"
        fun newInstance(): RegistrationFragment {
            return RegistrationFragment()
        }

    }


}
