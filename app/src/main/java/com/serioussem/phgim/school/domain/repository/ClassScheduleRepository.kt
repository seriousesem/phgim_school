package com.serioussem.phgim.school.domain.repository

import com.serioussem.phgim.school.domain.model.ClassScheduleModel
import com.serioussem.phgim.school.domain.core.Result

interface ClassScheduleRepository {

    suspend fun signIn(login: String, password: String): Result<Boolean>
    suspend fun loadAndSaveClassSchedule(): Result<Boolean>
    suspend fun fetchCurrentWeekClassSchedule(): Result<ClassScheduleModel>
    suspend fun fetchNextWeekClassSchedule(): Result<ClassScheduleModel>
    suspend fun fetchPreviousWeekClassSchedule(): Result<ClassScheduleModel>

}