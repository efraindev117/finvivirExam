package com.xsoftcdmx.finvivirexam.core.data.repository

import com.xsoftcdmx.finvivirexam.core.common.Resource
import com.xsoftcdmx.finvivirexam.core.model.geocoding.GeocodingResponseItem
import com.xsoftcdmx.finvivirexam.core.model.openweathermap.WeatherUiModel
import kotlinx.coroutines.flow.Flow

interface OpenWeatherRepository {
    fun getWeatherByCoordinates(
        lat: Double,
        lon: Double,
        units: String = "metric"
    ): Flow<Resource<WeatherUiModel>>

    fun getWeatherByNameCity(
        city: String,
        limit: Int = 1,
    ): Flow<Resource<GeocodingResponseItem>>
}