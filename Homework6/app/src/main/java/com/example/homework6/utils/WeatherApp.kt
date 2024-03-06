package com.example.homework6.utils

import android.app.Application
import com.example.homework6.di.DataContainer

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DataContainer.initDataDependency(ctx = this)
        DataContainer.initDomainDependencies()
    }
}