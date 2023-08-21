package com.serioussem.phgim.school.data.room.entity
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.serioussem.phgim.school.data.model.LessonsOfDayDataModel
import com.serioussem.phgim.school.utils.RoomConstants.CLASS_SCHEDULE_TABLE_NAME

@Entity(
    tableName = CLASS_SCHEDULE_TABLE_NAME
)
data class ClassScheduleEntity(
    @PrimaryKey
    val currentWeek: String,
    @Embedded
    val daysOfWeek: DaysOfWeek
)
data class DaysOfWeek(
    val lessonsOfDayList: List<LessonsOfDayDataModel>
)