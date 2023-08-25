package com.serioussem.phgim.school.domain.model

data class DayOfWeekModel(
    val dayIndex: Int,
    val lessonsOfDay: List<LessonModel>
)
