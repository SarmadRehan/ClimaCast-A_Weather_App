package com.sarmadrehan.climacast.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarmadrehan.climacast.models.current.CurrentWeather
import com.sarmadrehan.climacast.models.forecast.ForecastWeather
import com.sarmadrehan.climacast.repository.WeatherRepository
import com.sarmadrehan.climacast.utils.AppConstants.QUERY

import com.sarmadrehan.climacast.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherViewModel(
    val weatherRepository: WeatherRepository
) : ViewModel() {

    val currentWeather: MutableLiveData<Resource<CurrentWeather>> = MutableLiveData()
    val forecastWeather: MutableLiveData<Resource<ForecastWeather>> = MutableLiveData()

    init {
        getCurrentWeather(QUERY)
        getForecastWeather(QUERY,"2023-06-30")
    }

    fun getCurrentWeather(city: String) = viewModelScope.launch {
        currentWeather.postValue(Resource.Loading())
        val response = weatherRepository.getCurrentWeather(city)
        currentWeather.postValue(handleCurrentWeatherResponse(response))
    }
    fun getForecastWeather(city: String,duration: String) = viewModelScope.launch {
        forecastWeather.postValue(Resource.Loading())
        val response = weatherRepository.getForecastWeather(city,duration)
        forecastWeather.postValue(handleWeatherForecastResponse(response))
    }
    private fun handleCurrentWeatherResponse(response: Response<CurrentWeather>): Resource<CurrentWeather> {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleWeatherForecastResponse(response: Response<ForecastWeather>): Resource<ForecastWeather> {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}