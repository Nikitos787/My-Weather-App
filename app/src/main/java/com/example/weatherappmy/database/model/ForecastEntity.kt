package com.example.weatherappmy.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val locationName: String,
    val date: String,
    val maxTempC: String,
    val minTempC: String,
    val conditionText: String,
    val conditionIcon: String
)