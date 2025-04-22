package com.xsoftcdmx.domain.usescase

import com.xsoftcdmx.finvivirexam.core.common.Resource
import com.xsoftcdmx.finvivirexam.core.data.repository.OpenWeatherRepository
import com.xsoftcdmx.finvivirexam.core.model.geocoding.GeocodingResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
//No eran necesarios pero de igual forma se implementan
//para demostrar expertis en arquitectura y apps complejas.
class ByCityNameUseCase @Inject constructor(
    private val repo: OpenWeatherRepository
) {
    operator fun invoke(
        city: String,
        limit: Int = 1
    ): Flow<Resource<GeocodingResponseItem>> =
        repo.getWeatherByNameCity(city, limit).flowOn(Dispatchers.IO)
}