package com.xsoftcdmx.network.model.openweathermap


import com.google.gson.annotations.SerializedName

data class NetworkClouds(
    @SerializedName("all")
    val all: Int?
)