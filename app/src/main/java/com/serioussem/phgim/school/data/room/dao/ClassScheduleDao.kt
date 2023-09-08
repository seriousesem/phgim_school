package com.serioussem.phgim.school.data.room.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.serioussem.phgim.school.data.room.entity.ClassScheduleEntity
import com.serioussem.phgim.school.utils.RoomConstants.CLASS_SCHEDULE_TABLE_NAME


@Dao
interface ClassScheduleDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertClassSchedule(classScheduleEntity: ClassScheduleEntity)

    @Query("SELECT * FROM $CLASS_SCHEDULE_TABLE_NAME WHERE currentWeekId = :currentWeekId")
    suspend fun selectClassScheduleByCurrentWeekId(currentWeekId: String): ClassScheduleEntity

}
