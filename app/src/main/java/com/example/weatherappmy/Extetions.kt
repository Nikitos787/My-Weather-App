package com.example.weatherappmy

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.weatherappmy.database.model.CurrentWeatherEntity
import com.example.weatherappmy.database.model.ForecastEntity
import com.example.weatherappmy.database.model.HourEntity
import com.example.weatherappmy.model.WeatherCondition
import com.example.weatherappmy.model.WeatherCurrent
import com.example.weatherappmy.model.WeatherForecastDay
import com.example.weatherappmy.model.WeatherHour
import com.example.weatherappmy.model.WeatherLocation

fun Fragment.isPermissionGranted(parameter: String): Boolean {
    return ContextCompat
        .checkSelfPermission(
            activity as AppCompatActivity, parameter
        ) == PackageManager.PERMISSION_GRANTED
}

fun CurrentWeatherEntity.toWeatherCurrent(): WeatherCurrent = WeatherCurrent(
    this.lastUpdated,
    this.temperatureC,
    WeatherCondition(this.conditionText, this.conditionIcon)
)

fun CurrentWeatherEntity.toWeatherLocation(city: String): WeatherLocation = WeatherLocation(
    name = city,
    localtime = this.lastUpdated
)

fun WeatherCurrent.toCurrentWeatherEntity(locationName: String): CurrentWeatherEntity {
    return CurrentWeatherEntity(
        locationName = locationName,
        lastUpdated = this.last_updated,
        temperatureC = this.temp_c,
        conditionText = this.condition.text,
        conditionIcon = this.condition.icon
    )
}

fun WeatherForecastDay.toForecastEntity(city: String): ForecastEntity {
    return ForecastEntity(
        date = this.date,
        maxTempC = this.day.maxtemp_c,
        minTempC = this.day.mintemp_c,
        conditionText = this.day.condition.text,
        conditionIcon = this.day.condition.icon,
        locationName = city
    )
}

fun List<WeatherHour>.toHourEntities(forecastId: Long): List<HourEntity> {
    return this.map { hour ->
        HourEntity(
            time = hour.time,
            tempC = hour.temp_c,
            conditionText = hour.condition.text,
            conditionIcon = hour.condition.icon,
            forecastId = forecastId
        )
    }
}

fun List<HourEntity>.toWeatherHour(forecastId: Long): List<WeatherHour> {
    return this.map { hourEntity ->
        WeatherHour(
            time = hourEntity.time,
            condition = WeatherCondition(hourEntity.conditionText, hourEntity.conditionIcon),
            temp_c = hourEntity.tempC
        )
    }
}
