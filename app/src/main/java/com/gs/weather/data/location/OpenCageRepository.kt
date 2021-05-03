package com.gs.weather.data.location

import com.gs.weather.data.entity.Location
import com.gs.weather.utils.Result

class OpenCageRepository(
    private val locationDataSource: LocationDataSource
): LocationRepository {
    override suspend fun getLocationsByName(locationName: String): Result<List<Location>> {
        return locationDataSource.request(locationName)
    }
}