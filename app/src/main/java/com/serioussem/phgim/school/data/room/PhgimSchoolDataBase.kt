package com.serioussem.phgim.school.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.serioussem.phgim.school.data.room.dao.LessonDao
import com.serioussem.phgim.school.data.room.entity.LessonEntity

@Database(
    entities = [LessonEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PhgimSchoolDataBase : RoomDatabase() {
    abstract fun fetchLessonDao(): LessonDao
}