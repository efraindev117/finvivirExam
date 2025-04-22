package com.xsoftcdmx.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xsoftcdmx.database.dao.IWeatherDao
import com.xsoftcdmx.database.model.WeatherEntity

@Database(
    entities = [
        WeatherEntity::class
    ],
    version = 1,
    exportSchema = true,
)
internal abstract class FinvivirDatabase : RoomDatabase() {
    abstract fun weatherDao(): IWeatherDao
}