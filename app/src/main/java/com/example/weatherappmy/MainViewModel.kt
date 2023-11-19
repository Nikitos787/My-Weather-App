package com.example.weatherappmy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherappmy.database.service.WeatherService
import com.example.weatherappmy.model.WeatherResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val weatherService: WeatherService) : ViewModel() {
    private val _response = MutableLiveData<WeatherResponse?>()
    val response: MutableLiveData<WeatherResponse?>
        get() = _response
    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun updateWeather(city: String) {
        viewModelScope.launch {
            try {
                _response.value = weatherService.getWeatherDataFromApi(city)
            } catch (e: Exception) {
                _error.value = "Error receiving data: ${e.message}"
            }
        }
    }
}

class MainViewModelFactory(private val weatherService: WeatherService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(weatherService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
