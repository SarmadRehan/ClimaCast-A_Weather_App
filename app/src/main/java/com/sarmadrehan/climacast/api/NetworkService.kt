package com.sarmadrehan.climacast.api

import com.sarmadrehan.climacast.models.current.CurrentWeather
import com.sarmadrehan.climacast.models.forecast.ForecastWeather
import com.sarmadrehan.climacast.utils.AppConstants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("v1/current.json")
    suspend fun getCurrentWeather(
        @Query("q") query: String,
        @Query("key") apiKey : String = API_KEY
    ): Response<CurrentWeather>

    @GET("v1/future.json")
    suspend fun getForecastWeather(
        @Query("q") query: String,
        @Query("dt") duration: String,
        @Query("key") apiKey : String = API_KEY
    ): Response<ForecastWeather>


    /*  companion object {
          operator fun invoke : NetworkService{
              val requestInterceptor = Interceptor {chain ->
                  val url = chain.request()
                      .url
                      .newBuilder()
                      .addQueryParameter("key", API_KEY)
                      .build()
                  val request = chain.request()
                      .newBuilder()
                      .url(url)
                      .build()
                  return@Interceptor chain.proceed(request)
              }
              val okHttpClient:
          }

      }*/
}