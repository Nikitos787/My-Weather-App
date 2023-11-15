package com.example.weatherappmy.network

import com.example.weatherappmy.model.WeatherResponse
import com.example.weatherappmy.network.ApiService.getApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitImpl {
    suspend fun getWeatherData(city: String): WeatherResponse {
        return withContext(Dispatchers.IO) {
            getApiService().getWeatherInfoByCityName(
                "APIKEY",
                city,
                7,
                "no",
                "no"
            )
        }
    }
}