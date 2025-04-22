package com.xsoftcdmx.finvivirexam.core.model.geocoding

data class GeocodingResponseItem(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String
)
