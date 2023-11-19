package com.example.weatherappmy.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val locationName: String,
    val lastUpdated: String,
    val temperatureC: String,
    val conditionText: String,
    val conditionIcon: String
)
