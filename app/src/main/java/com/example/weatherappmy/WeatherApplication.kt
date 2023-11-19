package com.example.weatherappmy

import android.app.Application
import androidx.room.Room
import com.example.weatherappmy.database.dao.WeatherDatabase

class WeatherApplication : Application() {
    companion object {
        lateinit var database: WeatherDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java,
            "weather_database"
        ).build()
    }
}
