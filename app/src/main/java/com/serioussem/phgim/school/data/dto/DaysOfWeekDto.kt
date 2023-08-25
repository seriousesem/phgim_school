package com.serioussem.phgim.school.data.dto

data class DaysOfWeekDto (
    val dayIndex: Int,
    val lessonsOfDay: List<LessonDto>
)
