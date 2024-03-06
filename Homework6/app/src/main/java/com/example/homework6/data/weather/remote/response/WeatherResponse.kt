package com.example.homework6.data.weather.remote.response

import com.google.gson.annotations.SerializedName

class WeatherResponse {

    @SerializedName("weather")
    val weatherIconData: List<WeatherIconData?> = listOf()

    @SerializedName("main")
    val mainData: MainData? = null

}

class WeatherIconData {

    @SerializedName("main")
    val mainWeather : String? = null
    @SerializedName("description")
    val description : String? = null
    @SerializedName("icon")
    val srcImg: String? = null

}

class MainData {
    @SerializedName("temp")
    val temp : Float? = null
}