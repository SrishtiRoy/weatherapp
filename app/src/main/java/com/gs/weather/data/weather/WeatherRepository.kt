package com.gs.weather.data.weather

import com.gs.weather.data.entity.FavouriteForecast
import com.gs.weather.data.entity.Forecast
import com.gs.weather.data.entity.Location
import com.gs.weather.utils.Result
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getForecastById(id: Int): Result<Forecast>

    suspend fun getForecastByLocation(location: Location): Result<Forecast>

    suspend fun getUpdatedForecast(forecast: Forecast): Result<Forecast>

    suspend fun getObservableCurrentForecast(): Flow<Result<Forecast>>

    suspend fun getFavouriteForecasts(): Result<List<FavouriteForecast>>

    suspend fun updateFavouriteForecasts()

    suspend fun starEvent(forecast: Forecast, isStarred: Boolean)

    suspend fun isEmpty(): Boolean
}