package com.serioussem.phgim.school.data.room.converter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.serioussem.phgim.school.data.dto.DaysOfWeekDto

class RoomConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromListLessonsOfDayEntity(value: List<DaysOfWeekDto>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toListLessonsOfDayEntity(value: String): List<DaysOfWeekDto> {
        val listType = object : TypeToken<List<DaysOfWeekDto>>() {}.type
        return gson.fromJson(value, listType)
    }
}