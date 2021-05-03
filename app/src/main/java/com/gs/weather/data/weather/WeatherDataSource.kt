package com.gs.weather.data.weather

import com.gs.weather.base.NetworkDataSource
import com.gs.weather.data.entity.Forecast
import com.gs.weather.data.entity.Location
import com.gs.weather.utils.Result
import java.util.*

interface WeatherDataSource : NetworkDataSource {
    /**
     * Performs GET request to the OpenWeatherMap API to fetch new [Forecast]
     */
    suspend fun request(location: Location, locale: Locale): Result<Forecast>
}