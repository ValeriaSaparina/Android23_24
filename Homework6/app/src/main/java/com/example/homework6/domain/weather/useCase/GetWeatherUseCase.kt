package com.example.homework6.domain.weather.useCase

import com.example.homework6.domain.weather.mapper.WeatherUiModelMapper
import com.example.homework6.domain.weather.repository.WeatherRepository
import com.example.homework6.ui.weather.model.WeatherUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetWeatherUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: WeatherRepository,
    private val mapper: WeatherUiModelMapper,
) {
    suspend fun invoke(city: String) : WeatherUiModel {
        return withContext(dispatcher) {
            val weatherData = repository.getWeatherByCity(city)
            mapper.mapDomainToUiModel(weatherData)
        }
    }
}
