package com.example.homework6.domain.debug.mapper

import com.example.homework6.R
import com.example.homework6.domain.debug.model.DebugDataDomainModel
import com.example.homework6.ui.debug.model.DebugDataUiModel
import com.example.homework6.utils.ResManager

class DebugDataUiModelMapper(
    private val resManager: ResManager
) {
    fun mapDomainToUiModel(input: DebugDataDomainModel) : DebugDataUiModel{
        return DebugDataUiModel(
            appNameDebug = resManager.getString(R.string.app_name_debug, input.appName),
            baseUrlDebug = resManager.getString(R.string.base_url_debug, input.baseUrl),
            appVersionDebug = resManager.getString(R.string.app_version_debug, input.versionName, input.versionCode),
            deviceModelDebug = resManager.getString(R.string.device_model_debug, input.manufacturer, input.model),
            androidVersionDebug = resManager.getString(R.string.android_version_debug, input.versionAndroid, input.versionApi)
        )
    }
}
