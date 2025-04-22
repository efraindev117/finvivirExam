package com.xsoftcdmx.finvivirexam.core.model.openweathermap

data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)