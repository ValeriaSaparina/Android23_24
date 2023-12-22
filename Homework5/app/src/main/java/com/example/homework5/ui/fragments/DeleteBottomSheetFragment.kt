package com.example.homework5.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.homework5.helpers.Helper
import com.example.homework5.databinding.FragmentDeleteBottomSheetBinding
import com.example.homework5.helpers.ServiceLocator
import com.example.homework5.data.mappings.UserMapping
import com.example.homework5.data.models.UserModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DeleteBottomSheetFragment(
    private val user: UserModel,
    private val authorize: (user: UserModel) -> Unit
) : BottomSheetDialogFragment() {


    private var viewBinding: FragmentDeleteBottomSheetBinding? = null
    private var dbInstance = ServiceLocator.getDbInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentDeleteBottomSheetBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.apply {
            deleteBtn.setOnClickListener {
                lifecycleScope.launch {
                    async(Dispatchers.IO) {
                        dbInstance.userDao.deleteUser(UserMapping.toEntity(user))
                    }.await()
                }
                Helper.showNotification(context, "Account has been deleted")
            }

            restoreBtn.setOnClickListener {
                val userEntity = UserMapping.toEntity(user)
                userEntity.deletionTime = null
                lifecycleScope.launch {
                    async(Dispatchers.IO) {
                        dbInstance.userDao.updateUserDeletionTime(userEntity.userId)
                    }.await()
                }
                authorize(user)
                dismiss()
            }
        }
    }


    companion object {
        const val FRAGMENT_TAG = "Delete bottom sheet"

        fun newInstance(
            user: UserModel,
            authorize: (user: UserModel) -> Unit
        ): DeleteBottomSheetFragment {
            return DeleteBottomSheetFragment(user, authorize)
        }
    }
}