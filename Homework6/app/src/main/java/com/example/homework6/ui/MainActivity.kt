package com.example.homework6.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homework6.R
import com.example.homework6.ui.weather.WeatherFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, WeatherFragment())
            .commit()
    }
}