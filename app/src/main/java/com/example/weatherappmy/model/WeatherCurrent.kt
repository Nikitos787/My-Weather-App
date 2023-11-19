package com.example.weatherappmy.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class WeatherCurrent(
    val last_updated: String,
    val temp_c: String,
    val condition: WeatherCondition
)
