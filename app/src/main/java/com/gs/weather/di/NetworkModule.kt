package com.gs.weather.di

import com.gs.weather.api.LocationApi
import com.gs.weather.api.WeatherApi
import com.gs.weather.utils.OPEN_CAGE_URL
import com.gs.weather.utils.OPEN_WEATHER_MAP_URL
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(OPEN_CAGE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LocationApi::class.java)
    }
    single {
        Retrofit.Builder()
            .baseUrl(OPEN_WEATHER_MAP_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}