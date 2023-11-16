package com.example.weatherappmy.model

data class WeatherHour(
    val time: String,
    val condition: WeatherCondition,
    val temp_c: String
)
