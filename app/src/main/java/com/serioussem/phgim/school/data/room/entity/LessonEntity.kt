package com.serioussem.phgim.school.data.room.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.serioussem.phgim.school.utils.RoomNames.LESSON_TABLE_NAME

@Entity(
    tableName = LESSON_TABLE_NAME
)
data class LessonEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name", defaultValue = "")
    val name: String,
)
