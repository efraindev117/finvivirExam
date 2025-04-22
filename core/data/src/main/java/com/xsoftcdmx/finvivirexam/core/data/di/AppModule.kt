package com.xsoftcdmx.finvivirexam.core.data.di

import com.xsoftcdmx.finvivirexam.core.data.repository.OfflineFirstOpenWeatherRepository
import com.xsoftcdmx.finvivirexam.core.data.repository.OpenWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    internal abstract fun bindsOpenWeatherRepository(
        openWeatherRepository: OfflineFirstOpenWeatherRepository
    ): OpenWeatherRepository
}