package com.example.homework6.domain.weather.mapper

import com.example.homework6.domain.weather.model.WeatherDomainModel
import com.example.homework6.ui.weather.model.WeatherMainUiModel
import com.example.homework6.ui.weather.model.WeatherIconUiModel
import com.example.homework6.ui.weather.model.WeatherUiModel

class WeatherUiModelMapper {
    fun mapDomainToUiModel(input: WeatherDomainModel) : WeatherUiModel {
        return with(input) {
            WeatherUiModel(
                weatherIconData = WeatherIconUiModel(
                    mainWeather = weatherData.mainWeather,
                    description = weatherData.description,
                    srcImg = weatherData.srcImg
                ),
                mainData = WeatherMainUiModel(
                    temp = mainData.temp
                )
            )
        }
    }
}
