package com.serioussem.phgim.school.domain.model

data class ClassScheduleModel (
    val currentWeekId: String,
    val daysOfWeek: List<DayOfWeekModel>
)