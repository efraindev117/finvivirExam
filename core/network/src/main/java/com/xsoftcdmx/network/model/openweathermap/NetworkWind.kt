package com.xsoftcdmx.network.model.openweathermap


import com.google.gson.annotations.SerializedName

data class NetworkWind(
    @SerializedName("speed")
    val speed: Double?,
    @SerializedName("deg")
    val deg: Int?
)