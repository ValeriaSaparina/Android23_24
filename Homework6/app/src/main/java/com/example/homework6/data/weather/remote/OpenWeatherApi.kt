package com.example.homework6.data.weather.remote

import com.example.homework6.data.weather.remote.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String
    ) : WeatherResponse?

}