package com.example.weatherappmy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class WeatherCondition(
    val text: String,
    val icon: String
)
