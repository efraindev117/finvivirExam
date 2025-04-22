package com.xsoftcdmx.finvivirexam.core.data.model

import com.xsoftcdmx.database.model.CoordEntity
import com.xsoftcdmx.database.model.MainEntity
import com.xsoftcdmx.database.model.WeatherEntity
import com.xsoftcdmx.network.model.openweathermap.NetworkCoord
import com.xsoftcdmx.network.model.openweathermap.NetworkMain
import com.xsoftcdmx.network.model.openweathermap.NetworkOpenWeatherMapRs

fun NetworkCoord.toEntity(): CoordEntity =
    CoordEntity(
        lat = this.lat,
        lon = this.lon
    )

fun NetworkMain.toEntity(): MainEntity =
    MainEntity(
        temp       = this.temp,
        feelsLike  = this.feelsLike,
        tempMin    = this.tempMin,
        tempMax    = this.tempMax,
        pressure   = this.pressure,
        humidity   = this.humidity,
        seaLevel   = this.seaLevel,
        grndLevel  = this.grndLevel
    )

fun NetworkOpenWeatherMapRs.toEntity(): WeatherEntity =
    WeatherEntity(
        id       = this.id ?: 0,
        name     = this.name,
        timezone = this.timezone,
        cod      = this.cod,
        coord    = this.coord?.toEntity(),
        main     = this.main?.toEntity()
    )

