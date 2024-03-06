package com.example.homework6.ui.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.homework6.utils.observe
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

open class BaseFragment : Fragment() {
    protected fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), message, duration).show()
    }

    inline fun <T> Flow<T>.observe(crossinline block: (T) -> Unit): Job {
        return observe(fragment = this@BaseFragment, block)
    }
}