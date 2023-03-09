package com.example.myapplication.data.repository

import com.example.myapplication.data.model.response.WeatherResponse
import com.example.myapplication.data.network.OpenWeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository {

    suspend fun getWeatherInfoByCityName(city: String): WeatherResponse? {
        Result
        return withContext(Dispatchers.IO) {
            OpenWeatherService.getInstance()?.getWeatherByCityName(city = city)
        }
    }
    suspend fun getWeatherInfoByCoord(latitude: Double, longitude: Double): WeatherResponse?{
        Result
        return withContext(Dispatchers.IO) {
            OpenWeatherService.getInstance()?.getWeatherByCoord(latitude = latitude, longitude = longitude)
        }
    }
}