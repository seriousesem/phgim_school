package com.serioussem.phgim.school.domain.model

data class DayOfWeek(
    val dayIndex: Int,
    val lessonsOfDay: List<Lesson>
)
