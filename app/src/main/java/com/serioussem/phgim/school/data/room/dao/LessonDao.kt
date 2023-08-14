package com.serioussem.phgim.school.data.room.dao
import com.serioussem.phgim.school.data.room.entity.LessonEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.serioussem.phgim.school.utils.RoomNames.LESSON_TABLE_NAME


@Dao
interface LessonDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertLessons(threat: LessonEntity)

    @Query("SELECT * FROM $LESSON_TABLE_NAME")
    suspend fun selectAllLessons(): List<LessonEntity>

}
