package com.xsoftcdmx.network.model.openweathermap


import com.google.gson.annotations.SerializedName

data class NetworkOpenWeatherMapRs(
    @SerializedName("coord")
    val coord: NetworkCoord?,
    @SerializedName("weather")
    val weather: List<NetworkWeather?>?,
    @SerializedName("base")
    val base: String?,
    @SerializedName("main")
    val main: NetworkMain?,
    @SerializedName("visibility")
    val visibility: Int?,
    @SerializedName("wind")
    val wind: NetworkWind?,
    @SerializedName("clouds")
    val clouds: NetworkClouds?,
    @SerializedName("dt")
    val dt: Int?,
    @SerializedName("sys")
    val sys: NetworkSys?,
    @SerializedName("timezone")
    val timezone: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("cod")
    val cod: Int?
)