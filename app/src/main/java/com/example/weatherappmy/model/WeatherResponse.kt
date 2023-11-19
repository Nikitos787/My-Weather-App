package com.example.weatherappmy.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class WeatherResponse(
    val location: WeatherLocation,
    val current: WeatherCurrent,
    val forecast: WeatherForecast
)
