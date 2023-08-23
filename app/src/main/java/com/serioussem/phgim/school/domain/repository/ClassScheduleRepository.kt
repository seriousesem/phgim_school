package com.serioussem.phgim.school.domain.repository

import com.serioussem.phgim.school.domain.model.ClassSchedule
import com.serioussem.phgim.school.domain.core.Result

interface ClassScheduleRepository {

    suspend fun signIn(login: String, password: String): Result<Boolean>
    suspend fun loadAndSaveClassSchedule(): Result<Boolean>
    suspend fun fetchCurrentWeekClassSchedule(): Result<ClassSchedule>
    suspend fun fetchNextWeekClassSchedule(): Result<ClassSchedule>
    suspend fun fetchPreviousWeekClassSchedule(): Result<ClassSchedule>

}