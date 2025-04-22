package com.xsoftcdmx.network.api

import com.xsoftcdmx.network.model.geocoding.NetworkGeocodingResponseItem
import com.xsoftcdmx.network.model.openweathermap.NetworkOpenWeatherMapRs
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface INetworkDataSource {

    @GET("data/2.5/weather")
    suspend fun getWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric"
    ): Response<NetworkOpenWeatherMapRs>

    @GET("geo/1.0/direct")
    suspend fun getCoordinatesByNameCity(
        @Query("q") city: String,
        @Query("limit") limit: Int = 1,
    ): Response<List<NetworkGeocodingResponseItem>>
}