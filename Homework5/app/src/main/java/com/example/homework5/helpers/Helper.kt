package com.example.homework5.helpers

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import com.example.homework5.exceptions.InvalidInputData
import java.security.MessageDigest
import java.util.Calendar

object Helper {
    fun showNotification(ctx: Context?, message: String?) {
        Toast.makeText(ctx, message ?: "", Toast.LENGTH_LONG).show()
    }

    fun hashedPassword(password: String): String {
        val md = MessageDigest.getInstance(Params.ALGORITHM)
        return md.digest(
            password.toByteArray(Charsets.UTF_8)
        ).joinToString("")
    }

    fun isValidEmail(email: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true
        }
        throw InvalidInputData(Params.INVALID_INPUT)

    }

    fun isValidReleaseDate(releaseDate: Int): Boolean {
        if (releaseDate >= 1895 && releaseDate <= (Calendar.getInstance()
                .get(Calendar.YEAR) + 10)
        ) {
            return true
        }
        throw InvalidInputData(Params.INVALID_INPUT)
    }
}