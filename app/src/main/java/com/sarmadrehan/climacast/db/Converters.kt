package com.sarmadrehan.climacast.db

import androidx.room.TypeConverter
import com.sarmadrehan.climacast.models.Condition
import com.sarmadrehan.climacast.models.Current

class Converters {

    @TypeConverter
    fun fromCondition(condition: Condition): String{
        return condition.text
    }

    @TypeConverter
    fun toCondition(text: String): Condition{
        return Condition(0,text,text)
    }


}