package com.serioussem.phgim.school.data.dto

data class ClassScheduleDto(
    val currentWeekId: String,
    val daysOfWeek: List<DaysOfWeekDto>
)
