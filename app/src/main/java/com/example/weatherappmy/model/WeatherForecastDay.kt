package com.example.weatherappmy.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class WeatherForecastDay(
    val date: String,
    val day: WeatherDay,
    val hour: List<WeatherHour>
)
