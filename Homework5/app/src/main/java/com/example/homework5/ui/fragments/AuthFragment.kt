package com.example.homework5.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.homework5.data.db.entities.UserEntity
import com.example.homework5.data.mappings.UserMapping
import com.example.homework5.data.models.UserModel
import com.example.homework5.databinding.FragmentAuthBinding
import com.example.homework5.exceptions.EmptyFieldsException
import com.example.homework5.exceptions.InvalidInputData
import com.example.homework5.helpers.CurrentUser
import com.example.homework5.helpers.Helper
import com.example.homework5.helpers.Params
import com.example.homework5.helpers.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthFragment : Fragment() {

    private var viewBinding: FragmentAuthBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAuthBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        viewBinding?.apply {

            val dbInstance = ServiceLocator.getDbInstance()

            authBtn.setOnClickListener {
                try {

                    checkNotEmptyFields()
                    val email = emailEt.text.toString()
                    Helper.isValidEmail(email)
                    lifecycleScope.launch {
                        try {
                            var userEntity: UserEntity?
                            withContext(Dispatchers.IO) {
                                userEntity =
                                    dbInstance.userDao.getUserByEmail(email)
                            }

                            checkNotNull(userEntity)

                            val user: UserModel = UserMapping.toModel(userEntity!!)

                            if (user.email != emailEt.text.toString() || user.password != Helper.hashedPassword(
                                    passwordEt.text.toString()
                                )
                            ) {
                                throw InvalidInputData(Params.INCORRECT_PASSWORD)
                            }

                            if (userEntity!!.deletionTime != null) {

                                val bottomSheet =
                                    DeleteBottomSheetFragment.newInstance(user, ::authorize)
                                bottomSheet.show(
                                    parentFragmentManager,
                                    DeleteBottomSheetFragment.FRAGMENT_TAG
                                )

                            } else {
                                authorize(user)
                            }
                        } catch (e: IllegalStateException) {
                            Helper.showNotification(context, Params.INCORRECT_PASSWORD)
                        } catch (e: InvalidInputData) {
                            Helper.showNotification(context, e.message)
                        }
                    }
                } catch (e: EmptyFieldsException) {
                    Helper.showNotification(context, e.message)
                } catch (e: InvalidInputData) {
                    Helper.showNotification(context, e.message)
                }
            }



            regBtn.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(
                        Params.containerViewId,
                        RegistrationFragment.newInstance(),
                        RegistrationFragment.FRAGMENT_TAG
                    )
                    .commit()
            }
        }
    }

    private fun authorize(user: UserModel) {
        CurrentUser.setCurrentUser(user)
        ServiceLocator.getSharedPreferencesInstance().edit()
            .putLong(Params.AUTH_SESSION, user.userId).apply()

        parentFragmentManager.beginTransaction()
            .replace(
                Params.containerViewId,
                MoviesFragment.newInstance(),
                MoviesFragment.FRAGMENT_TAG
            )
            .commit()
    }

    private fun checkNotEmptyFields() {
        viewBinding?.apply {
            if (emailEt.text.toString().isEmpty() || passwordEt.text.toString()
                    .isEmpty()
            ) throw EmptyFieldsException()
        }
    }

    companion object {

        const val FRAGMENT_TAG = "auth fragment"
        fun newInstance(): AuthFragment {
            return AuthFragment()
        }
    }

}