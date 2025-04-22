package com.xsoftcdmx.network.model.openweathermap


import com.google.gson.annotations.SerializedName

data class NetworkCoord(
    @SerializedName("lon")
    val lon: Double?,
    @SerializedName("lat")
    val lat: Double?
)