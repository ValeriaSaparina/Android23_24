package com.example.homework6.data.weather.remote.interceptor

import com.example.homework6.base.Constants
import com.example.homework6.base.Keys
import okhttp3.Interceptor
import okhttp3.Response

class MetricInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url.newBuilder().addQueryParameter(
            name = Keys.UNITS_KEY,
            value = Constants.METRIC
        ).build()

        val requestBuilder = chain.request().newBuilder().url(newUrl)
        return chain.proceed(requestBuilder.build())
    }
}