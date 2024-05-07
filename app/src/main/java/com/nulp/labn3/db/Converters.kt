package com.nulp.labn3.db

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

    @TypeConverter
    fun fromTimeString(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it, DateTimeFormatter.ofPattern("HH:mm")) }
    }

    @TypeConverter
    fun timeToTimeString(time: LocalTime?): String? {
        return time?.format(DateTimeFormatter.ofPattern("HH:mm"))
    }
}