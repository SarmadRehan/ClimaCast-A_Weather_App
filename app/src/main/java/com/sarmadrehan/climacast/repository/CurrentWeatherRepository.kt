package com.sarmadrehan.climacast.repository

import com.sarmadrehan.climacast.api.NetworkClient
import com.sarmadrehan.climacast.api.NetworkService
import com.sarmadrehan.climacast.db.WeatherDatabase
import retrofit2.Retrofit

class CurrentWeatherRepository(val db: WeatherDatabase) {
    suspend fun getCurrentWeather(city:String) = NetworkClient.api.getCurrentWeather(city)
}