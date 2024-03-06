package com.example.homework6.data.debug.repository

import android.os.Build
import com.example.homework6.BuildConfig
import com.example.homework6.R
import com.example.homework6.domain.debug.model.DebugDataDomainModel
import com.example.homework6.domain.debug.repository.DebugDataRepository
import com.example.homework6.utils.ResManager

class DebugDataRepositoryImpl(
    private val resManager: ResManager
) : DebugDataRepository {
    override fun getDebugData(): DebugDataDomainModel {
        return DebugDataDomainModel(
            appName = resManager.getString(R.string.app_name),
            baseUrl = BuildConfig.OPEN_WEATHER_BASE_URL,
            versionName = BuildConfig.VERSION_NAME,
            versionCode = BuildConfig.VERSION_CODE.toString(),
            manufacturer = Build.MANUFACTURER,
            model = Build.MODEL,
            versionAndroid = Build.VERSION.RELEASE,
            versionApi = Build.VERSION.SDK_INT.toString()
        )
    }
}