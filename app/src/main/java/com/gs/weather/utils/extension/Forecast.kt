package com.gs.weather.utils.extension

import com.gs.weather.data.entity.FavouriteForecast
import com.gs.weather.data.entity.Forecast
import com.gs.weather.data.entity.Location

fun Forecast.toLocation() = Location(locationName, longitude, latitude)

fun Forecast.toFavouriteForecast() = FavouriteForecast(
    id = id,
    temperature = currentForecast.temperature,
    description = currentForecast.description,
    locationName = locationName,
    imageId = currentForecast.imageId
)