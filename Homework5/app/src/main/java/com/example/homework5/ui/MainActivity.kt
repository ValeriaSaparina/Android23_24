package com.example.homework5.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.homework5.helpers.CurrentUser
import com.example.homework5.helpers.Params
import com.example.homework5.R
import com.example.homework5.helpers.ServiceLocator
import com.example.homework5.ui.fragments.AuthFragment
import com.example.homework5.ui.fragments.MoviesFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val dbInstance = ServiceLocator.getDbInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {

            val userForDeletion = async(Dispatchers.IO) {
                dbInstance.userDao.getUsersForDelete()
            }.await()

            userForDeletion?.forEach {
                async(Dispatchers.IO) {
                    dbInstance.userDao.deleteUser(it)
                }.await()
            }
        }

        if (savedInstanceState == null) {
            val userId = userIsAuth()
            if (userId != -1L) {
                lifecycleScope.launch {
                    val job = launch {
                        CurrentUser.setCurrentUser(userId)
                    }
                    job.join()

                    supportFragmentManager.beginTransaction()
                        .add(
                            Params.containerViewId,
                            MoviesFragment.newInstance(),
                            MoviesFragment.FRAGMENT_TAG
                        )
                        .commit()
                }
            } else {
                supportFragmentManager.beginTransaction()
                    .add(
                        Params.containerViewId,
                        AuthFragment.newInstance(),
                        AuthFragment.FRAGMENT_TAG
                    )
                    .commit()
            }
        }

    }

    private fun userIsAuth(): Long {
        return ServiceLocator.getSharedPreferencesInstance().getLong(Params.AUTH_SESSION, -1L)
    }
}
