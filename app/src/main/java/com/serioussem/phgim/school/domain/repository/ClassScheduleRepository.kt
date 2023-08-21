package com.serioussem.phgim.school.domain.repository

import com.serioussem.phgim.school.domain.model.ClassSchedule
import com.serioussem.phgim.school.domain.util.Result

interface ClassScheduleRepository {

    suspend fun loadAndSaveClassSchedule(): Result<Boolean>
    suspend fun fetchCurrentWeekClassSchedule(): Result<ClassSchedule>
    suspend fun fetchNextWeekClassSchedule(): Result<ClassSchedule>
    suspend fun fetchPreviousWeekClassSchedule(): Result<ClassSchedule>
}