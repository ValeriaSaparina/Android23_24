package com.example.homework6.di

import android.content.Context
import com.example.homework6.BuildConfig
import com.example.homework6.data.debug.repository.DebugDataRepositoryImpl
import com.example.homework6.data.weather.ExceptionHandlerDelegate
import com.example.homework6.data.weather.mapper.WeatherDomainModelMapper
import com.example.homework6.data.weather.remote.OpenWeatherApi
import com.example.homework6.data.weather.remote.interceptor.AppIdInterceptor
import com.example.homework6.data.weather.remote.interceptor.MetricInterceptor
import com.example.homework6.data.weather.repository.WeatherRepositoryImpl
import com.example.homework6.domain.debug.mapper.DebugDataUiModelMapper
import com.example.homework6.domain.debug.repository.DebugDataRepository
import com.example.homework6.domain.debug.useCase.GetDebugDataUseCase
import com.example.homework6.domain.weather.mapper.WeatherUiModelMapper
import com.example.homework6.domain.weather.useCase.GetWeatherUseCase
import com.example.homework6.utils.ResManager
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference

object DataContainer {

    private var okHttpClient: OkHttpClient? = null
    private var openWeatherApi: OpenWeatherApi? = null

    private val dispatcherIO = Dispatchers.IO

    private var weatherRepository: WeatherRepositoryImpl? = null
    private var debugDataRepository: DebugDataRepository? = null

    private val weatherDomainModelMapper = WeatherDomainModelMapper()
    private val weatherUiModelMapper = WeatherUiModelMapper()

    private var debugDataUiModelMapper: DebugDataUiModelMapper? = null

    var getWeatherUseCase: GetWeatherUseCase? = null
    var getDebugDataUseCase: GetDebugDataUseCase? = null

    var exceptionHandlerDelegate: ExceptionHandlerDelegate? = null


    private var ctxRef: WeakReference<Context>? = null


    fun initDataDependency(ctx: Context) {
        ctxRef = WeakReference(ctx)

        exceptionHandlerDelegate = ExceptionHandlerDelegate(getResManager())

        buildOkHttpClient()
        initWeatherApi()

        initDebugDataUiMapper()
        initRepositories()



    }

    private fun initRepositories() {
        if (openWeatherApi != null) {
            weatherRepository = WeatherRepositoryImpl(
                openWeatherApi!!,
                weatherDomainModelMapper
            )
        } else {
            throw IllegalStateException("weatherApi is not initialized")
        }

        debugDataRepository = DebugDataRepositoryImpl(getResManager())
    }

    private fun initDebugDataUiMapper() {
        debugDataUiModelMapper = DebugDataUiModelMapper(getResManager())
    }

    private fun getResManager(): ResManager {
        return ResManager(ctx = provideContext())
    }

    private fun provideContext(): Context {
        return ctxRef?.get() ?: throw IllegalStateException("Context is null")
    }

    private fun initWeatherApi() {
        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.OPEN_WEATHER_BASE_URL)
            .client(okHttpClient!!)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        openWeatherApi = builder.create(OpenWeatherApi::class.java)
    }

    fun initDomainDependencies() {
        if (weatherRepository != null) {
            getWeatherUseCase =
                GetWeatherUseCase(dispatcherIO, weatherRepository!!, weatherUiModelMapper)

        } else {
            throw IllegalStateException("weatherRepository is not initialized")
        }

        if (debugDataRepository != null && debugDataUiModelMapper != null) {
            getDebugDataUseCase = GetDebugDataUseCase(repository = debugDataRepository!!, mapper = debugDataUiModelMapper!!)
        } else {
            throw IllegalStateException("debugDataRepository or debugDataUiModelMapper are not initialized")
        }
    }

    private fun buildOkHttpClient() {
        val clientBuilder = OkHttpClient.Builder().addInterceptor(AppIdInterceptor())
            .addInterceptor(MetricInterceptor())
        okHttpClient = clientBuilder.build()
    }


}