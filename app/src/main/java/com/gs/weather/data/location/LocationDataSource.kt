package com.gs.weather.data.location

import com.gs.weather.base.NetworkDataSource
import com.gs.weather.data.entity.Location
import com.gs.weather.utils.Result

interface LocationDataSource : NetworkDataSource {
    suspend fun request(locationName: String): Result<List<Location>>
}