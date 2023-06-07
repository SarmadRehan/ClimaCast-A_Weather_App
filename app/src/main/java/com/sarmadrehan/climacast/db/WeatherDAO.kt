package com.sarmadrehan.climacast.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarmadrehan.climacast.models.current.Current

//DAO-DataAccessObject
@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(weather: Current): Long       //upsert: Insert + Update

    @Query("Select * from weather")
    fun  getWeather(): LiveData<Current>

//    @Delete
//    suspend fun deleteWeather(weather: Current)
}