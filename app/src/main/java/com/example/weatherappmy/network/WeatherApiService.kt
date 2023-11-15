package com.example.weatherappmy.network

import com.example.weatherappmy.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast.json")
    suspend fun getWeatherInfoByCityName(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String
    ) : WeatherResponse
}