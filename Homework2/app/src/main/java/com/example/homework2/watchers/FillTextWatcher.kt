package com.example.homework2.watchers

import android.text.Editable
import android.text.TextWatcher
import com.example.homework2.databinding.FragmentStartBinding

open class FillTextWatcher(vb: FragmentStartBinding?) : TextWatcher {

    private val viewBinding = vb

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {
        viewBinding?.apply {
            if (!inputEt.text.isNullOrEmpty() && phoneEt.text?.length == 18) {
                clickMeBtn.isClickable = true
            }
        }
    }
}