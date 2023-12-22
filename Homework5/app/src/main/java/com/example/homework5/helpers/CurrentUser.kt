package com.example.homework5.helpers

import com.example.homework5.data.db.HwDatabase
import com.example.homework5.exceptions.DataNotFoundException
import com.example.homework5.data.mappings.UserMapping
import com.example.homework5.data.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CurrentUser {

    private var user: UserModel? = null
    private var dbInstance: HwDatabase = ServiceLocator.getDbInstance()

    fun getCurrentUser(): UserModel {
        return user ?: throw IllegalStateException("User is not authorized")
    }

    suspend fun setCurrentUser(userId: Long) {
        withContext(Dispatchers.IO) {
            user = UserMapping.toModel(
                dbInstance.userDao.getUserById(userId = userId) ?: throw DataNotFoundException()
            )
        }
    }

    fun setCurrentUser(userModel: UserModel) {
        user = userModel
    }

    fun getCurrentUserId(): Long = user?.userId ?: -1L

}