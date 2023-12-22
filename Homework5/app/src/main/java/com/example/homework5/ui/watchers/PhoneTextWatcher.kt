package com.example.homework5.ui.watchers

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.example.homework5.R

class PhoneTextWatcher(view: View) : TextWatcher {

    private val phoneEt = view.findViewById<EditText>(R.id.phone_et)
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        var text = phoneEt?.text.toString()
        var length = text.length

        if (p3 == 0) {
            text = phoneEt?.text.toString()
            length = text.length
            when (length) {
                13, 16 -> {
                    text = phoneEt?.text.toString()
                    length = text.length
                    phoneEt?.setText(text.substring(0, length - 1))

                }

                8 -> {
                    text = phoneEt?.text.toString()
                    length = text.length
                    val rr = text.substring(0, length - 2)
                    phoneEt?.setText(rr)

                }

            }
        } else {

            when (length) {
                7 -> phoneEt?.setText("$text) ")

                12, 15 -> phoneEt?.setText("$text-")
            }

        }

        if (length < 4) phoneEt?.setText("+7 (")
        length = phoneEt?.text?.length ?: 0
        phoneEt?.apply {
            setSelection(length)
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }

}