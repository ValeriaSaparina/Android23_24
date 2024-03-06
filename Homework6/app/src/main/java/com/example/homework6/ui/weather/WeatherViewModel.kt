package com.example.homework6.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.homework6.data.weather.ExceptionHandlerDelegate
import com.example.homework6.data.weather.runCatching
import com.example.homework6.di.DataContainer
import com.example.homework6.domain.weather.useCase.GetWeatherUseCase
import com.example.homework6.ui.weather.model.WeatherUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
) : ViewModel() {

    private val _loadingWeather = MutableStateFlow(false)
    val loadingWeather: StateFlow<Boolean> get() = _loadingWeather

    private val _error = MutableStateFlow<Throwable?>(null)
    val error: StateFlow<Throwable?> get() = _error

    private val _weatherUiModel = MutableStateFlow<WeatherUiModel?>(null)
    val weatherUiModel: StateFlow<WeatherUiModel?> get() = _weatherUiModel


    fun onLoadWeatherClicked(city: String) {
        loadWeather(city)
    }

    private fun loadWeather(city: String) {

        viewModelScope.launch {

            runCatching(exceptionHandlerDelegate) {
                _loadingWeather.emit(true)
                getWeatherUseCase.invoke(city).also { weatherInfo ->
                    _weatherUiModel.emit(weatherInfo)
                }
            }.onFailure {
                _error.emit(it)
                _weatherUiModel.emit(null)
            }.onSuccess {
                _error.emit(null)
            }
            _loadingWeather.emit(false)
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val useCase = DataContainer.getWeatherUseCase
                val exceptionHandler = DataContainer.exceptionHandlerDelegate
                if (useCase != null && exceptionHandler != null) {
                    WeatherViewModel(useCase, exceptionHandler)
                } else {
                    throw IllegalStateException("useCase is not initialized")
                }
            }
        }
    }

}
