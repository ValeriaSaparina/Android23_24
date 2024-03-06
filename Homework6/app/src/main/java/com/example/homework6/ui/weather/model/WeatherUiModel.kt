package com.example.homework6.ui.weather.model


data class WeatherUiModel (

    val weatherIconData: WeatherIconUiModel? = null,

    val mainData: WeatherMainUiModel? = null

    )

data class WeatherIconUiModel (

    val mainWeather: String? = null,
    val description: String? = null,
    val srcImg: String? = null

)

class WeatherMainUiModel (
    val temp: Float? = null
)
