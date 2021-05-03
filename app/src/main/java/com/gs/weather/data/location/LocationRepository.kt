package com.gs.weather.data.location

import com.gs.weather.data.entity.Location
import com.gs.weather.utils.Result

interface LocationRepository {
    /**
     * Performs GET request to the OpenCage API to fetch locations
     */
    suspend fun getLocationsByName(locationName: String): Result<List<Location>>
}