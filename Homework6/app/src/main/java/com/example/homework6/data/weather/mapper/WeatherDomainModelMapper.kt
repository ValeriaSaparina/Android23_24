package com.example.homework6.data.weather.mapper

import com.example.homework6.base.Constants
import com.example.homework6.data.weather.remote.response.WeatherResponse
import com.example.homework6.domain.weather.model.WeatherDomainModel
import com.example.homework6.domain.weather.model.WeatherIconDataDomainModel
import com.example.homework6.domain.weather.model.WeatherMainDataDomainModel

class WeatherDomainModelMapper {
    fun mapResponseToDomainModel(input: WeatherResponse?): WeatherDomainModel? {
        return input?.let {
            WeatherDomainModel(
                mainData = WeatherMainDataDomainModel(
                    temp = it.mainData?.temp ?: Constants.EMPTY_TEMP_FLOAT
                ),
                weatherData = WeatherIconDataDomainModel(
                    mainWeather = it.weatherIconData[0]?.mainWeather ?: "",
                    description = it.weatherIconData[0]?.description ?: "",
                    srcImg = it.weatherIconData[0]?.srcImg ?: ""
                )
            )
        }
    }

}
