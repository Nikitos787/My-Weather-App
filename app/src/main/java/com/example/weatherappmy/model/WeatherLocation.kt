package com.example.weatherappmy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class WeatherLocation(
    val name: String,
    val localtime: String
)
