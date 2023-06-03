package com.sarmadrehan.climacast.api

import com.sarmadrehan.climacast.models.CurrentWeather
import com.sarmadrehan.climacast.utils.AppConstants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("v1/current.json")
    suspend fun getCurrentWeather(
        @Query("q") locationName: String,
        @Query("key") apiKey : String = API_KEY
    ): Response<CurrentWeather>
}