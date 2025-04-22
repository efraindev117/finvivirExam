package com.xsoftcdmx.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.xsoftcdmx.network.api.INetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder().serializeNulls().setLenient().create()

    @Provides
    @Named("openWeatherApiKey")
    @Singleton
    fun provideOpenWeatherApiKey(): String =
        "63ccc5d4de148a91b376bf260b058699"

    @Provides
    @Singleton
    fun providesInterceptor(@Named("openWeatherApiKey") apiKey: String
    ): Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val urlWithKey = originalUrl.newBuilder()
            .addQueryParameter("appid", apiKey)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(urlWithKey)
            .build()
        chain.proceed(newRequest)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        interceptor: Interceptor
    ): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
        return httpBuilder
            .protocols(mutableListOf(Protocol.HTTP_1_1))
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofitInstance(
        client: OkHttpClient,
        gson: Gson
    ): INetworkDataSource {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(INetworkDataSource::class.java)
    }
}