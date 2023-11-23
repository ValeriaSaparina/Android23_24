package com.example.homework3.utils

import android.widget.EditText

object Helper {

    const val maxNews = 45
    const val maxExtraNews = 5

    fun textChangedListener(et: EditText, max: Int) {
        val text = try {
            et.text.toString().toInt()
        } catch (ex: NumberFormatException) {
            0
        }
        if (text > max) {
            et.error = "number should be less than $max"
        }
    }

}