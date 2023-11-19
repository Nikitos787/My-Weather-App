package com.example.weatherappmy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherappmy.database.model.CurrentWeatherEntity
import com.example.weatherappmy.database.model.ForecastEntity
import com.example.weatherappmy.database.model.HourEntity

@Dao
interface CurrentWeatherDao {
    @Insert
    suspend fun insertCurrentWeather(currentWeather: CurrentWeatherEntity)

    @Query("SELECT * FROM current_weather WHERE locationName = :locationName")
    suspend fun getCurrentWeather(locationName: String): CurrentWeatherEntity?
}

@Dao
interface ForecastDao {
    @Insert
    suspend fun insertForecast(forecast: ForecastEntity)

    @Query("SELECT * FROM forecast WHERE locationName = :locationName")
    suspend fun getForecast(locationName: String): List<ForecastEntity>
}

@Dao
interface HourDao {
    @Query("SELECT * FROM hour WHERE forecastId = :forecastId")
    suspend fun getHourlyForecast(forecastId: Long): List<HourEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyForecast(hourEntities: List<HourEntity>)
}