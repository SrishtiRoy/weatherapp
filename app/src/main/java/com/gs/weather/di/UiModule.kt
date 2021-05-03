package com.gs.weather.di

import com.gs.weather.data.entity.FavouriteForecast
import com.gs.weather.data.entity.Location
import com.gs.weather.ui.favourite.FavouriteAdapter
import com.gs.weather.ui.search.SearchAdapter
import com.gs.weather.ui.weather.DailyForecastAdapter
import com.gs.weather.ui.weather.HourlyForecastAdapter
import org.koin.dsl.module

val uiModule = module {
    factory { HourlyForecastAdapter() }
    factory { DailyForecastAdapter() }
    factory { (onLocationChange: (Location) -> Unit) -> SearchAdapter(onLocationChange) }
    factory { (onForecastChange: (FavouriteForecast) -> Unit) -> FavouriteAdapter(onForecastChange) }
}