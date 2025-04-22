package com.xsoftcdmx.database.di

import com.xsoftcdmx.database.FinvivirDatabase
import com.xsoftcdmx.database.dao.IWeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesTopicsDao(
        database: FinvivirDatabase,
    ): IWeatherDao = database.weatherDao()
}