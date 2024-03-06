package com.example.homework6.domain.weather.repository

import com.example.homework6.domain.weather.model.WeatherDomainModel

interface WeatherRepository {

    suspend fun getWeatherByCity(city: String) : WeatherDomainModel


}