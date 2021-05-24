package com.gs.weather.utils

import android.content.Context
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.gs.weather.data.AppSettings
import com.gs.weather.data.AppSettingsImpl
import com.gs.weather.data.db.AppDataBase
import com.gs.weather.data.location.LocationDataSource
import com.gs.weather.data.location.LocationRepository
import com.gs.weather.data.location.OpenCageDataSource
import com.gs.weather.data.location.OpenCageRepository
import com.gs.weather.api.LocationApi
import com.gs.weather.api.WeatherApi
import com.gs.weather.data.weather.OpenWeatherMapDataSource
import com.gs.weather.data.weather.OpenWeatherMapRepository
import com.gs.weather.data.weather.WeatherDataSource
import com.gs.weather.ui.WeatherViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * [Context] object used for tests
 */
val context: Context
    get() = InstrumentationRegistry.getInstrumentation().targetContext

/**
 * [AppDataBase] object used for tests
 */
val appDatabase: AppDataBase
    get() = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
        .allowMainThreadQueries()
        .build()

/**
 * [WeatherApi] object used for tests
 */
val weatherApi: WeatherApi
    get() = Retrofit.Builder()
        .baseUrl(OPEN_WEATHER_MAP_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

/**
 * [LocationApi] object used for tests
 */
val locationApi: LocationApi
    get() = Retrofit.Builder()
        .baseUrl(OPEN_CAGE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LocationApi::class.java)

/**
 * [OpenWeatherMapDataSource] object used for tests
 */
val weatherDataSource: WeatherDataSource
    get() = OpenWeatherMapDataSource(weatherApi)

/**
 * [OpenWeatherMapRepository] object used for tests
 */
val weatherRepository: OpenWeatherMapRepository
    get() = OpenWeatherMapRepository(weatherDataSource, appDatabase, appSettings)

val locationDataSource: LocationDataSource
    get() = OpenCageDataSource(locationApi)
/**
 * [LocationRepository] object used for tests
 */
val locationRepository: LocationRepository
    get() = OpenCageRepository(locationDataSource)

/**
 * [AppSettings] object used for tests
 */
val appSettings: AppSettings
    get() = AppSettingsImpl(context)

/**
 * [WeatherViewModel] object used for tests
 */
val weatherViewModel: WeatherViewModel
    get() = WeatherViewModel(weatherRepository, appSettings)