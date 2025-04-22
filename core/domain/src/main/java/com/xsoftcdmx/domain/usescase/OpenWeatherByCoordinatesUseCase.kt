package com.xsoftcdmx.domain.usescase

import com.xsoftcdmx.finvivirexam.core.common.Resource
import com.xsoftcdmx.finvivirexam.core.data.repository.OpenWeatherRepository
import com.xsoftcdmx.finvivirexam.core.model.openweathermap.WeatherUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//No eran necesarios pero de igual forma se implementan
//para demostrar expertis en arquitectura y apps complejas.

class OpenWeatherByCoordinatesUseCase @Inject constructor(
    private val repository: OpenWeatherRepository
) {
    operator fun invoke(
        lat: Double,
        lon: Double,
        units: String = "metric"
    ): Flow<Resource<WeatherUiModel>> =
        repository
            .getWeatherByCoordinates(lat, lon, units)
            .flowOn(Dispatchers.IO)
}