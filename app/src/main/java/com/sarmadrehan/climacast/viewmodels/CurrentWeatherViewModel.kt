package com.sarmadrehan.climacast.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarmadrehan.climacast.models.CurrentWeather
import com.sarmadrehan.climacast.repository.CurrentWeatherRepository
import com.sarmadrehan.climacast.utils.AppConstants.CITY
import com.sarmadrehan.climacast.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class CurrentWeatherViewModel(
    val currentWeatherRepository: CurrentWeatherRepository
) : ViewModel() {

    val currentWeather: MutableLiveData<Resource<CurrentWeather>> = MutableLiveData()

    init {
        getCurrentWeather(CITY)
    }

    fun getCurrentWeather(city: String) = viewModelScope.launch {
        currentWeather.postValue(Resource.Loading())
        val response = currentWeatherRepository.getCurrentWeather(city)
        currentWeather.postValue(handleCurrentWeatherResponse(response))
    }
    private fun handleCurrentWeatherResponse(response: Response<CurrentWeather>): Resource<CurrentWeather> {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}