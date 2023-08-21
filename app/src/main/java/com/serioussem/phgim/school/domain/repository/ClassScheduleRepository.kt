package com.serioussem.phgim.school.domain.repository

import com.serioussem.phgim.school.domain.model.ClassSchedule
import com.serioussem.phgim.school.domain.util.Result

interface ClassScheduleRepository {
    suspend fun fetchClassSchedule(currentWeek: String): Result<ClassSchedule>
}