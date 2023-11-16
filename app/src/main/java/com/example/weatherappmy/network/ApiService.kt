package com.example.weatherappmy.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.weatherapi.com/v1/"
object ApiService {
    private var service: WeatherApiService? = null

    fun getApiService(): WeatherApiService {
        if (service == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            service = retrofit.create(WeatherApiService::class.java)
        }
        return service!!
    }
}
