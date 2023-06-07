package com.sarmadrehan.climacast.db

import androidx.room.TypeConverter
import com.sarmadrehan.climacast.models.current.Condition

class Converters {

    @TypeConverter
    fun fromCondition(condition: Condition): String{
        return condition.text
    }

    @TypeConverter
    fun toCondition(text: String): Condition {
        return Condition(0,text,text)
    }


}