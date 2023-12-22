package com.example.homework4

import android.app.NotificationManager
import android.util.Log
import android.widget.RadioButton
import androidx.core.app.NotificationCompat

object Settings {
    private var isShowFullMessage: Boolean = false
    private var isShowTwoButtons: Boolean = false
    private var importance: Int = NotificationManager.IMPORTANCE_DEFAULT
    private var visibility: Int = NotificationCompat.VISIBILITY_PUBLIC

    const val IMPORTANCE_HIGH_CHANNEL: String = "IMPORTANCE_HIGH_CHANNEL"
    const val IMPORTANCE_DEFAULT_CHANNEL: String = "IMPORTANCE_DEFAULT_CHANNEL"
    const val IMPORTANCE_LOW_CHANNEL: String = "IMPORTANCE_LOW_CHANNEL"

    fun setImportance(rb: RadioButton) {
        val value = rb.text.toString()
        importance = when (value) {
            "Urgent" -> NotificationManager.IMPORTANCE_HIGH
            "High" -> NotificationManager.IMPORTANCE_DEFAULT
            "Medium" -> NotificationManager.IMPORTANCE_LOW

            else -> NotificationManager.IMPORTANCE_DEFAULT
        }
    }

    fun setVisibility(rb: RadioButton) {
        val value = rb.text.toString()
        visibility = when (value) {
            "Public" -> NotificationCompat.VISIBILITY_PUBLIC
            "Secret" -> NotificationCompat.VISIBILITY_SECRET
            "Private" -> NotificationCompat.VISIBILITY_PRIVATE

            else -> NotificationCompat.VISIBILITY_PUBLIC
        }
    }

    fun setIsShowFullMessage(isShow: Boolean = false) {
        isShowFullMessage = isShow
    }

    fun setIsShowTwoButtons(isButtons: Boolean = false) {
        isShowTwoButtons = isButtons
    }

    fun getImportance() = importance

    fun getVisibility() = visibility

    fun getIsShowFullMessage() = isShowFullMessage

    fun getIsShowTwoButton() = isShowTwoButtons
}
