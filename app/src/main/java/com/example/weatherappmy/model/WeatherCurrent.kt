package com.example.weatherappmy.model

data class WeatherCurrent(
    val last_updated: String,
    val temp_c: String,
    val condition: WeatherCondition
)
