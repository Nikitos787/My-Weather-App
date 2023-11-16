package com.example.weatherappmy.network

import com.example.weatherappmy.model.WeatherResponse
import com.example.weatherappmy.network.ApiService.getApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val API_KEY = "YOUR_API_KEY"
const val NUMBER_OF_DAYS = 7
const val AQI_NO = "no"
const val ALERTS_NO = "no"

class RetrofitImpl {
    suspend fun getWeatherData(city: String): WeatherResponse {
        return withContext(Dispatchers.IO) {
            getApiService().getWeatherInfoByCityName(
                API_KEY,
                city,
                NUMBER_OF_DAYS,
                AQI_NO,
                ALERTS_NO
            )
        }
    }
}
