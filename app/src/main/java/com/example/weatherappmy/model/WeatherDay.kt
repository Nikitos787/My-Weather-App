package com.example.weatherappmy.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class WeatherDay(
    val maxtemp_c: String,
    val mintemp_c: String,
    val condition: WeatherCondition
)
