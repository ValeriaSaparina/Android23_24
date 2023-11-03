package com.example.homework3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework3.fragments.StartFragment

class MainActivity : AppCompatActivity() {
    private val fragmentContainerId = R.id.main_activity_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(fragmentContainerId, StartFragment.newInstance())
                .commit()
        }
    }
}