package br.com.tecnomotor.thanos.database

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }


    // Conversores para Calendar
    @TypeConverter
    fun toLong(value: Calendar?): Long? {
        return value?.timeInMillis
    }

    @TypeConverter
    fun toCalendar(value: Long?): Calendar? {
        val currentMoment = Calendar.getInstance()
        if (value != null) {
            currentMoment.timeInMillis = value
        }
        return currentMoment
    }
}