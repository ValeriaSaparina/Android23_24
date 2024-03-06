package com.example.homework6.domain.weather.model

import com.example.homework6.base.Constants

data class WeatherDomainModel(
    val mainData: WeatherMainDataDomainModel,
    val weatherData: WeatherIconDataDomainModel,
)

fun WeatherDomainModel.isEmptyResponse(): Boolean {
    val isMainDataEmpty = with(this.mainData) {
        temp == Constants.EMPTY_TEMP_FLOAT
    }
    val isWeatherDataEmpty = with(this.weatherData) {
        mainWeather.isEmpty() && description.isEmpty() && srcImg.isEmpty()
    }
    return isMainDataEmpty && isWeatherDataEmpty
}

