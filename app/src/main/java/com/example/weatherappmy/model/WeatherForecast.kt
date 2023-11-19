package com.example.weatherappmy.model

import androidx.room.Embedded
import androidx.room.Entity

data class WeatherForecast(
    val forecastday: List<WeatherForecastDay>
)
