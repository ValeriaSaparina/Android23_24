package com.example.homework1

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val firstFragmentContainerId: Int = R.id.first_container_view
    private val fourthFragmentContainerId: Int = R.id.fourth_container_view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(ParamKeys.DEBUG, "activity onCreate()")

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    firstFragmentContainerId,
                    FirstFragment.newInstance(),
                    FirstFragment.FIRST_FRAGMENT_TAG
                ).commit()

            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Log.d(ParamKeys.DEBUG, "hotizont")
                supportFragmentManager.beginTransaction()
                    .add(
                        fourthFragmentContainerId,
                        FourthFragment.newInstance(),
                        FourthFragment.FOURTH_FRAGMENT_TAG
                    ).commit()
            }
        }
    }

}