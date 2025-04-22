package com.xsoftcdmx.finvivirexam.core.model.openweathermap

data class OpenWeatherMapRs(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

data class WeatherUiModel(
    val cityName: String,
    val temperature: Double,
    val feelsLike: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val humidity: Int,
    val pressure: Int,
    val seaLevel: Int,
    val groundLevel: Int,
    val latitude: Double,
    val longitude: Double,
    val timezone: Int
)