package com.example.weatherappmy.database.service

import com.example.weatherappmy.database.dao.CurrentWeatherDao
import com.example.weatherappmy.database.dao.ForecastDao
import com.example.weatherappmy.database.dao.HourDao
import com.example.weatherappmy.database.dao.WeatherDatabase
import com.example.weatherappmy.model.WeatherCondition
import com.example.weatherappmy.model.WeatherCurrent
import com.example.weatherappmy.model.WeatherDay
import com.example.weatherappmy.model.WeatherForecast
import com.example.weatherappmy.model.WeatherForecastDay
import com.example.weatherappmy.model.WeatherLocation
import com.example.weatherappmy.model.WeatherResponse
import com.example.weatherappmy.network.RetrofitImpl
import com.example.weatherappmy.toCurrentWeatherEntity
import com.example.weatherappmy.toForecastEntity
import com.example.weatherappmy.toHourEntities
import com.example.weatherappmy.toWeatherCurrent
import com.example.weatherappmy.toWeatherHour
import com.example.weatherappmy.toWeatherLocation
import java.lang.Exception

class WeatherService(
    weatherDatabase: WeatherDatabase,
    private val retrofitImpl: RetrofitImpl
) {
    private val currentWeatherDao: CurrentWeatherDao = weatherDatabase.currentWeatherDao()
    private val forecastDao: ForecastDao = weatherDatabase.forecastDao()
    private val hourDao: HourDao = weatherDatabase.hourDao()

    suspend fun getWeatherDataFromApi(city: String): WeatherResponse {
        try {
            val response = retrofitImpl.getWeatherData(city)
            currentWeatherDao.insertCurrentWeather(response.current.toCurrentWeatherEntity(city))
            response.forecast.forecastday.forEach { forecaseday ->
                val forecastEntity = forecaseday.toForecastEntity(city)
                forecastDao.insertForecast(forecastEntity)

                hourDao.insertHourlyForecast(forecaseday.hour.toHourEntities(forecastEntity.id))
            }
            return response
        } catch (e: Exception) {
            return getWeatherDataFromDatabase(city)
        }
    }

    suspend fun getWeatherDataFromDatabase(city: String): WeatherResponse {
        val currentWeather = currentWeatherDao.getCurrentWeather(city)?.toWeatherCurrent()
        val location = currentWeatherDao.getCurrentWeather(city)?.toWeatherLocation(city)
        val forecastWeatherEntities = forecastDao.getForecast(city)
        val listOfWeaForecastDay = mutableListOf<WeatherForecastDay>()
        for (i in forecastWeatherEntities.indices) {
            val item = WeatherForecastDay(
                forecastWeatherEntities[i].date,
                WeatherDay(
                    forecastWeatherEntities[i].maxTempC,
                    forecastWeatherEntities[i].minTempC,
                    WeatherCondition(
                        forecastWeatherEntities[i].conditionText,
                        forecastWeatherEntities[i].conditionIcon,
                    )
                ),
                hourDao.getHourlyForecast(forecastWeatherEntities[i].id)
                    .toWeatherHour(forecastWeatherEntities[i].id)
            )
            listOfWeaForecastDay.add(item)
        }
        return WeatherResponse(
            location ?: WeatherLocation("", ""),
            currentWeather ?: WeatherCurrent(
            "",
            "",
            WeatherCondition("", "")
        ), WeatherForecast(listOfWeaForecastDay))
    }
}