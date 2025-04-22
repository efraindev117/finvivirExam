package com.xsoftcdmx.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xsoftcdmx.database.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather WHERE weather_id = :id")
    suspend fun getWeatherById(id: Int): WeatherEntity?

    @Query("SELECT * FROM weather WHERE coord_lat = :lat AND coord_lon = :lon LIMIT 1")
    suspend fun getWeatherByCoordinates(lat: Double, lon: Double): WeatherEntity?

    @Query("SELECT * FROM weather WHERE city_name = :cityName LIMIT 1")
    suspend fun getWeatherByCityName(cityName: String): WeatherEntity?

    @Query("SELECT * FROM weather")
    fun getAllWeather(): Flow<List<WeatherEntity>>

    @Query("DELETE FROM weather")
    suspend fun clearAll()
}