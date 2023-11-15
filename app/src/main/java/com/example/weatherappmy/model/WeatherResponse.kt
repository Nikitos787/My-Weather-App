package com.example.weatherappmy.model

data class WeatherResponse(
    val location: WeatherLocation,
    val current: WeatherCurrent,
    val forecast: WeatherForecast
)