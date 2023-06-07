package com.sarmadrehan.climacast.repository

import com.sarmadrehan.climacast.api.NetworkClient
import com.sarmadrehan.climacast.db.WeatherDatabase

class WeatherRepository(val db: WeatherDatabase) {
    suspend fun getCurrentWeather(query:String) = NetworkClient.api.getCurrentWeather(query)
    suspend fun getForecastWeather(query: String,duration: String) = NetworkClient.api.getForecastWeather(query,duration)
}