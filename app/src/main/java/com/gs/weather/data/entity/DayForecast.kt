package com.gs.weather.data.entity

import androidx.annotation.Keep
import androidx.room.TypeConverters
import com.gs.weather.data.db.Converters

@Keep
data class DayForecast(
    @TypeConverters(Converters::class)
    var highestTemp: Temperature,
    @TypeConverters(Converters::class)
    var lowestTemp: Temperature,
    val day: String,
    val date: String,
    @TypeConverters(Converters::class)
    val wind: Wind,
    @TypeConverters(Converters::class)
    val pressure: Pressure,
    val humidity: Int,
    val imageId: Int
)
