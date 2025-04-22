package com.xsoftcdmx.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.xsoftcdmx.finvivirexam.core.model.openweathermap.WeatherUiModel

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey
    @ColumnInfo(name = "weather_id")
    val id: Int,

    @ColumnInfo(name = "city_name")
    val name: String?,

    @ColumnInfo(name = "timezone")
    val timezone: Int?,

    @ColumnInfo(name = "cod")
    val cod: Int?,

    @Embedded(prefix = "coord_")
    val coord: CoordEntity?,

    @Embedded(prefix = "main_")
    val main: MainEntity?
)

data class CoordEntity(
    @ColumnInfo(name = "lat")
    val lat: Double?,

    @ColumnInfo(name = "lon")
    val lon: Double?
)

data class MainEntity(
    @ColumnInfo(name = "temp")
    val temp: Double?,

    @ColumnInfo(name = "feels_like")
    val feelsLike: Double?,

    @ColumnInfo(name = "temp_min")
    val tempMin: Double?,

    @ColumnInfo(name = "temp_max")
    val tempMax: Double?,

    @ColumnInfo(name = "pressure")
    val pressure: Int?,

    @ColumnInfo(name = "humidity")
    val humidity: Int?,

    @ColumnInfo(name = "sea_level")
    val seaLevel: Int?,

    @ColumnInfo(name = "grnd_level")
    val grndLevel: Int?
)

fun WeatherEntity.toDomainModel(): WeatherUiModel {
    return WeatherUiModel(
        cityName = name.orEmpty(),
        temperature = main?.temp ?: 0.0,
        feelsLike = main?.feelsLike ?: 0.0,
        minTemp = main?.tempMin ?: 0.0,
        maxTemp = main?.tempMax ?: 0.0,
        humidity = main?.humidity ?: 0,
        pressure = main?.pressure ?: 0,
        seaLevel = main?.seaLevel ?: 0,
        groundLevel = main?.grndLevel ?: 0,
        latitude = coord?.lat ?: 0.0,
        longitude = coord?.lon ?: 0.0,
        timezone = timezone ?: 0
    )
}