package com.example.weatherappmy.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hour")
data class HourEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val forecastId: Long,
    val time: String,
    val tempC: String,
    val conditionText: String,
    val conditionIcon: String
)