package com.xsoftcdmx.finvivirexam.core.data.repository

import com.xsoftcdmx.database.dao.IWeatherDao
import com.xsoftcdmx.database.model.toDomainModel
import com.xsoftcdmx.finvivirexam.core.common.BaseRepository
import com.xsoftcdmx.finvivirexam.core.common.Resource
import com.xsoftcdmx.finvivirexam.core.common.doOnSuccess
import com.xsoftcdmx.finvivirexam.core.common.map
import com.xsoftcdmx.finvivirexam.core.data.model.toDomainModel
import com.xsoftcdmx.finvivirexam.core.data.model.toEntity
import com.xsoftcdmx.finvivirexam.core.model.geocoding.GeocodingResponseItem
import com.xsoftcdmx.finvivirexam.core.model.openweathermap.WeatherUiModel
import com.xsoftcdmx.network.api.INetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class OfflineFirstOpenWeatherRepository @Inject constructor(
    private val database: IWeatherDao,
    private val network: INetworkDataSource
) : OpenWeatherRepository, BaseRepository() {

    override fun getWeatherByCoordinates(
        lat: Double,
        lon: Double,
        units: String
    ): Flow<Resource<WeatherUiModel>> = flow {
        emit(Resource.Loading)
        database.getWeatherByCoordinates(lat, lon)
            ?.toDomainModel()
            ?.let { emit(Resource.Success(it)) }
        val networkFlow: Flow<Resource<WeatherUiModel>> = safeApiCall {
            network.getWeatherByCoordinates(lat, lon, units)
        }.doOnSuccess { networkModel ->
                database.insertWeather(networkModel.toEntity())
            }.map { resource ->
                resource.map { networkModel ->
                    networkModel
                        .toEntity()
                        .toDomainModel()
                }
            }
        emitAll(networkFlow)
    }.flowOn(Dispatchers.IO)

    override fun getWeatherByNameCity(
        city: String,
        limit: Int
    ): Flow<Resource<GeocodingResponseItem>> =
        safeApiCall { network.getCoordinatesByNameCity(city, limit) }
            .transform { resource ->
                when (resource) {
                    is Resource.Loading -> emit(Resource.Loading)

                    is Resource.Failure ->
                        emit(Resource.Failure(resource.msg))

                    is Resource.Success -> {
                        val list = resource.data
                        if (list.isNotEmpty()) {
                            val domain = list.first().toDomainModel()
                            emit(Resource.Success(domain))
                        } else {
                            emit(Resource.Failure(NoSuchElementException("Ciudad no encontrada")))
                        }
                    }
                }
            }
}
