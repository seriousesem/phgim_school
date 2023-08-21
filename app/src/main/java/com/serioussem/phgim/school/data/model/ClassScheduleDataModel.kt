package com.serioussem.phgim.school.data.model

data class ClassScheduleDataModel(
    val currentWeek: String,
    val daysOfWeek: List<LessonsOfDayDataModel>
)
