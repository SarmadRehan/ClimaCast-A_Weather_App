package com.sarmadrehan.climacast.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sarmadrehan.climacast.repository.WeatherRepository
import com.sarmadrehan.climacast.viewmodels.WeatherViewModel

class CurrentWeatherViewModelProviderFactory(val weatherRepository: WeatherRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(weatherRepository) as T
    }
}