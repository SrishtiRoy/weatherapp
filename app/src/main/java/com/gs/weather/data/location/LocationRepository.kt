package com.gs.weather.data.location

import com.gs.weather.data.entity.Location
import com.gs.weather.utils.Result

interface LocationRepository {

    suspend fun getLocationsByName(locationName: String): Result<List<Location>>
}