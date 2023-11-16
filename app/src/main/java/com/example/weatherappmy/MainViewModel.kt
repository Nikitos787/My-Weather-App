package com.example.weatherappmy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherappmy.model.WeatherResponse
import com.example.weatherappmy.network.RetrofitImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {
    private val retrofitImpl = RetrofitImpl()
    private val _response = MutableLiveData<WeatherResponse>()
    val response: MutableLiveData<WeatherResponse>
        get() = _response
    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun updateWeatherData(city: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _response.value = retrofitImpl.getWeatherData(city)
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
            }
        }
    }
}
