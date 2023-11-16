package com.example.weatherappmy.model

data class WeatherForecastDay(
    val date: String,
    val day: WeatherDay,
    val hour: List<WeatherHour>
)
