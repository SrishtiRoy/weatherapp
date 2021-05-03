package com.gs.weather.di

import com.gs.weather.data.AppSettings
import com.gs.weather.data.AppSettingsImpl
import com.gs.weather.data.db.DatabaseFactory
import com.gs.weather.data.location.LocationDataSource
import com.gs.weather.data.location.LocationRepository
import com.gs.weather.data.location.OpenCageDataSource
import com.gs.weather.data.location.OpenCageRepository
import com.gs.weather.data.weather.OpenWeatherMapDataSource
import com.gs.weather.data.weather.OpenWeatherMapRepository
import com.gs.weather.data.weather.WeatherDataSource
import com.gs.weather.data.weather.WeatherRepository
import com.gs.weather.ui.WeatherViewModel
import com.gs.weather.ui.favourite.FavouriteViewModel
import com.gs.weather.ui.search.SearchViewModel
import com.gs.weather.ui.settings.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { WeatherViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { FavouriteViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    single<LocationDataSource> { OpenCageDataSource(get()) }
    single<LocationRepository> { OpenCageRepository(get()) }
    single<WeatherDataSource> { OpenWeatherMapDataSource(get()) }
    single<WeatherRepository> { OpenWeatherMapRepository(get(), get(), get()) }
    single<AppSettings> { AppSettingsImpl(androidApplication()) }
    single { DatabaseFactory.create(androidApplication()) }
}