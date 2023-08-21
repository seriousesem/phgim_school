package com.serioussem.phgim.school.data.room.converter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.serioussem.phgim.school.data.model.LessonsOfDayDataModel

class RoomConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromListLessonsOfDayEntity(value: List<LessonsOfDayDataModel>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toListLessonsOfDayEntity(value: String): List<LessonsOfDayDataModel> {
        val listType = object : TypeToken<List<LessonsOfDayDataModel>>() {}.type
        return gson.fromJson(value, listType)
    }
}