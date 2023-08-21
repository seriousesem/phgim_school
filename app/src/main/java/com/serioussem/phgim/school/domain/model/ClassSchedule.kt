package com.serioussem.phgim.school.domain.model

data class ClassSchedule (
    val currentWeek: String,
    val daysOfWeek: List<LessonsOfDay>
)