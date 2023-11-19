package com.example.weatherappmy.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class WeatherHour(
    val time: String,
    val condition: WeatherCondition,
    val temp_c: String
)
