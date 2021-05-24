package com.gs.weather.data.weather

import com.gs.weather.data.AppSettings
import com.gs.weather.data.db.AppDataBase
import com.gs.weather.data.entity.FavouriteForecast
import com.gs.weather.data.entity.Forecast
import com.gs.weather.data.entity.Location
import com.gs.weather.utils.Result
import com.gs.weather.utils.Result.Error
import com.gs.weather.utils.Result.Success
import com.gs.weather.utils.exception.DataNotFound
import com.gs.weather.utils.extension.toFavouriteForecast
import com.gs.weather.utils.extension.toLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OpenWeatherMapRepository(
    private val weatherDataSource: WeatherDataSource,
    private val database: AppDataBase,
    private val settings: AppSettings
) : WeatherRepository {

    override suspend fun getForecastById(id: Int): Result<Forecast> =
        withContext(Dispatchers.Default) {
            val forecast: Forecast = database.getForecastDao().getForecasts().find { it.id == id }!!

            database.getForecastDao().updateCurrentForecast(forecast.locationName)

            if (forecast.isOutdated) {
                return@withContext weatherDataSource
                    .request(forecast.toLocation(), settings.locale)
                    .onSuccess {
                        it.isFavourite = forecast.isFavourite
                        it.id = forecast.id
                        database.getForecastDao().updateForecast(it)
                    }
            }
            return@withContext Success(forecast)
        }

    override suspend fun getForecastByLocation(location: Location): Result<Forecast> =
        withContext(Dispatchers.Default) {
            // Forecast can be null if user chose new location (in search)
            val forecast: Forecast? = database.getForecastDao().getForecasts()
                .find { it.locationName == location.locationName }

            // Changing displayed (current) forecast on the main screen
            database.getForecastDao().updateCurrentForecast(location.locationName)

            if (forecast != null) {
                if (!forecast.isOutdated) {
                    return@withContext Success(forecast)
                } else {
                    return@withContext getUpdatedForecast(forecast)
                }
            } else {
                return@withContext weatherDataSource
                    .request(location, settings.locale)
                    .onSuccess { database.getForecastDao().insertForecast(it) }
            }
        }

    /**
     * Returns result of requesting new forecast by given old [forecast] information
     */
    override suspend fun getUpdatedForecast(forecast: Forecast): Result<Forecast> {
        return weatherDataSource
            .request(forecast.toLocation(), settings.locale)
            .onSuccess {
                it.id = forecast.id
                it.isFavourite = forecast.isFavourite
                database.getForecastDao().updateForecast(it)
            }
    }


    override suspend fun getObservableCurrentForecast(): Flow<Result<Forecast>> = flow {
        val currentForecast = database.getForecastDao().getCurrentForecast()
        if (currentForecast != null) {
            emit(Success(currentForecast))
            if (currentForecast.isOutdated) {
                emit(getUpdatedForecast(currentForecast))
            }
        } else {
            emit(Error(DataNotFound))
        }
    }


    override suspend fun isEmpty(): Boolean = database.getForecastDao().getForecasts().isEmpty()

    override suspend fun getFavouriteForecasts(): Result<List<FavouriteForecast>> =
        withContext(Dispatchers.Default) {
            val favouriteForecasts = database.getForecastDao().getFavouriteForecasts()
            return@withContext if (favouriteForecasts.isNotEmpty()) {
                Success(favouriteForecasts.map(Forecast::toFavouriteForecast))
            } else {
                Error(DataNotFound)
            }
        }

    /**
     * Updates forecast for favourite locations asynchronously
     */
    override suspend fun updateFavouriteForecasts() = coroutineScope {
        val favouriteForecasts: List<Forecast> = database.getForecastDao().getFavouriteForecasts()
        for (oldForecast in favouriteForecasts) {
            if (oldForecast.isOutdated) {
                launch {
                    getUpdatedForecast(oldForecast)
                }
            }
        }
    }

    /**
     * Changes forecast favourite status (isFavourite) in database
     */
    override suspend fun starEvent(forecast: Forecast, isStarred: Boolean) {
        database.getForecastDao().setForecastFavouriteStatus(forecast.locationName, isStarred)
    }
}