package com.example.homework6.data.weather.repository

import com.example.homework6.data.weather.mapper.WeatherDomainModelMapper
import com.example.homework6.data.weather.remote.OpenWeatherApi
import com.example.homework6.domain.weather.model.WeatherDomainModel
import com.example.homework6.domain.weather.model.isEmptyResponse
import com.example.homework6.domain.weather.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherApi: OpenWeatherApi,
    private val weatherDomainModelMapper: WeatherDomainModelMapper,
) : WeatherRepository {
    override suspend fun getWeatherByCity(city: String): WeatherDomainModel {
        val domainModel = weatherDomainModelMapper.mapResponseToDomainModel(
            input = weatherApi.getWeatherByCity(city)
        )
        return if (domainModel != null && domainModel.isEmptyResponse().not()) {
            domainModel
        } else {
            throw Exception("empty response")
        }
    }
}