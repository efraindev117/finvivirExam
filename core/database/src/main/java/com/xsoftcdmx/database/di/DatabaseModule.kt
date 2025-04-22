package com.xsoftcdmx.database.di

import android.content.Context
import androidx.room.Room
import com.xsoftcdmx.database.FinvivirDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): FinvivirDatabase = Room.databaseBuilder(
        context,
        FinvivirDatabase::class.java,
        "finvivir-database",
    ).build()
}