package com.example.homework5

import android.app.Application
import com.example.homework5.helpers.ServiceLocator

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.initData(ctx = this)
    }
}