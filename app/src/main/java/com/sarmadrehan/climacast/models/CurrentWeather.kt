package com.sarmadrehan.climacast.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CurrentWeather(
    val current: Current,
    val location: Location
)