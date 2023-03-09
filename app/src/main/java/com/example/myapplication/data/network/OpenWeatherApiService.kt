package com.example.myapplication.data.network

import com.example.myapplication.data.model.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {

    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") city: String
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeatherByCoord(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): WeatherResponse

}
