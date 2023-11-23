package com.example.homework2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework2.fragments.StartFragment

class MainActivity : AppCompatActivity() {

    private val containerId: Int = R.id.container_view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    containerId,
                    StartFragment.newInstance(),
                    StartFragment.START_FRAGMENT_TAG
                ).commit()
        }

    }
}