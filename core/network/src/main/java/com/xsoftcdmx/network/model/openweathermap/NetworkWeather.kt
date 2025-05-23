package com.xsoftcdmx.network.model.openweathermap


import com.google.gson.annotations.SerializedName

data class NetworkWeather(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("main")
    val main: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("icon")
    val icon: String?
)