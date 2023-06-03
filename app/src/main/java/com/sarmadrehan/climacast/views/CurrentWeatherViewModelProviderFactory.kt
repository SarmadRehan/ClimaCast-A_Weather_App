package com.sarmadrehan.climacast.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sarmadrehan.climacast.repository.CurrentWeatherRepository
import com.sarmadrehan.climacast.viewmodels.CurrentWeatherViewModel

class CurrentWeatherViewModelProviderFactory(val currentWeatherRepository: CurrentWeatherRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(currentWeatherRepository) as T
    }
}