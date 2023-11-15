package com.example.weatherappmy.model

data class WeatherDay(
    val maxtemp_c: String,
    val mintemp_c: String,
    val condition: WeatherCondition
)