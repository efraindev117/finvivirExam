package com.xsoftcdmx.gmaps

import com.xsoftcdmx.finvivirexam.core.model.geocoding.GeocodingResponseItem
import com.xsoftcdmx.finvivirexam.core.model.openweathermap.WeatherUiModel

data class GMapsUiState(
    val geo: GeocodingResponseItem? = null,
    val weather: WeatherUiModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)