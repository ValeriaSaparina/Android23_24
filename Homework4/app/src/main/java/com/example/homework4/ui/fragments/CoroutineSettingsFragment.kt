package com.example.homework4.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.homework4.R
import com.example.homework4.databinding.FragmentCoroutineSettingsBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutineSettingsFragment : Fragment(R.layout.fragment_coroutine_settings) {
    private var viewBinding: FragmentCoroutineSettingsBinding? = null
    private var completedCoroutines = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentCoroutineSettingsBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewBinding?.startBtn?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null).replace(R.id.container_view, NotificationSettingsFragment())
                .commit()
        }

        viewBinding?.apply {
            startBtn.setOnClickListener {
                val progress = seekbar.progress
                val isAsync = isAsyncChb.isChecked
                startCoroutines(n = progress, isAsync = isAsync)
            }

        }

    }

    private fun incrementCompletedCoroutines() {
        completedCoroutines += 1
    }

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }

    private suspend fun funSuspend(delay: Long = 3000) {
        withContext(Dispatchers.IO) {
            delay(delay)
            incrementCompletedCoroutines()
        }
    }

    private fun isNotAsyncStart(n: Int) {
        lifecycleScope.launch {
            for (i in 1..n) {
                funSuspend(delay = 1000L * i)
            }
            Toast.makeText(
                context,
                "All $completedCoroutines coroutines were completed",
                Toast.LENGTH_LONG
            ).show()
            completedCoroutines = 0
        }


    }

    private fun asyncStart(n: Int) {
        lifecycleScope.launch {
            val list: MutableList<Deferred<Any>> = mutableListOf()
            for (i in 1..n) {
                list.add(
                    (lifecycleScope.async {
                        funSuspend(delay = 1000L * i)
                    })
                )
            }
            list.awaitAll()
            Toast.makeText(
                context,
                "All $completedCoroutines coroutines were completed",
                Toast.LENGTH_SHORT
            ).show()

        }
        completedCoroutines = 0
    }

    private fun startCoroutines(n: Int, isAsync: Boolean = true) {
        if (isAsync) asyncStart(n)
        else isNotAsyncStart(n)
    }

    fun stopCoroutines() {

        var isStop = false
        viewBinding?.apply {
            isStop = isStopChb.isChecked
        }
        if (isStop) {
            lifecycleScope.coroutineContext.cancelChildren()
            completedCoroutines = 0
        }

    }

    companion object {
        private var coroutineSettingsFragment: CoroutineSettingsFragment? = null

        fun getInstance(): CoroutineSettingsFragment {
            if (coroutineSettingsFragment == null) coroutineSettingsFragment =
                CoroutineSettingsFragment()
            return coroutineSettingsFragment!!
        }

    }

}