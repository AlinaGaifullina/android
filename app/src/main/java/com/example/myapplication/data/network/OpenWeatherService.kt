package com.example.myapplication.data.network

import androidx.viewbinding.BuildConfig

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenWeatherService {

    private var okHttpClient: OkHttpClient? = null
    private var retrofitInstance: OpenWeatherApiService? = null

    private const val WEATHER_KEY = "09f6e9f88648878a96a344ad59b920b3"
    private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private fun getOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val modifiedUrl = chain.request().url.newBuilder()
                    .addQueryParameter("appid", WEATHER_KEY)
                    .addQueryParameter("units", "metric")
                    .build()

                val request = chain.request().newBuilder().url(modifiedUrl).build()
                chain.proceed(request)
            }
        if (BuildConfig.DEBUG) {
            client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return client.build()
    }

    private fun createRetrofitInstance(): OpenWeatherApiService {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .client(okHttpClient ?: OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitBuilder.create(OpenWeatherApiService::class.java)
    }

    private fun getRetrofitInstance() {
        if (okHttpClient == null) {
            okHttpClient = getOkHttpClient()
        }
        retrofitInstance = createRetrofitInstance()
    }

    fun getInstance(): OpenWeatherApiService? {
        return if (retrofitInstance == null) {
            getRetrofitInstance()
            retrofitInstance
        } else retrofitInstance
    }
}
