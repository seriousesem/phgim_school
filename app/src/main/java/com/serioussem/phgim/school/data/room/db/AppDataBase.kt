package com.serioussem.phgim.school.data.room.db
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.serioussem.phgim.school.data.room.converter.RoomConverters
import com.serioussem.phgim.school.data.room.dao.ClassScheduleDao
import com.serioussem.phgim.school.data.room.entity.ClassScheduleEntity
import com.serioussem.phgim.school.utils.RoomConstants.DATABASE_VERSION

@Database(
    entities = [ClassScheduleEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun fetchClassScheduleDao(): ClassScheduleDao
}