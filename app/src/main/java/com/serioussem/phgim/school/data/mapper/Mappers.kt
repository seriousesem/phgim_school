package com.serioussem.phgim.school.data.mapper

import com.serioussem.phgim.school.data.dto.ClassScheduleDto
import com.serioussem.phgim.school.data.dto.LessonDto
import com.serioussem.phgim.school.data.dto.DaysOfWeekDto
import com.serioussem.phgim.school.data.room.entity.ClassScheduleEntity
import com.serioussem.phgim.school.data.room.entity.DaysOfWeek
import com.serioussem.phgim.school.domain.model.ClassScheduleModel
import com.serioussem.phgim.school.domain.model.DayOfWeekModel
import com.serioussem.phgim.school.domain.model.LessonModel

fun ClassScheduleDto.toClassScheduleEntity(): ClassScheduleEntity {
    return ClassScheduleEntity(
        currentWeekId = currentWeekId,
        daysOfWeek = DaysOfWeek(lessonsOfDayList = daysOfWeek)
    )
}

fun LessonDto.toLessonModel(): LessonModel {
    return LessonModel(
        lessonName = lessonName,
        homeWork = homeWork,
        mark = mark
    )
}

fun DaysOfWeekDto.toDayOfWeekModel(): DayOfWeekModel {
    return DayOfWeekModel(
        dayIndex = dayIndex,
        lessonsOfDay = lessonsOfDay.map {
            it.toLessonModel()
        }.toList()
    )
}

fun ClassScheduleDto.toClassScheduleModel(): ClassScheduleModel {
    return ClassScheduleModel(
        currentWeekId = currentWeekId,
        daysOfWeek = daysOfWeek.map { daysOfWeekDto ->
            daysOfWeekDto.toDayOfWeekModel()
        }.toList()
    )
}

fun ClassScheduleEntity.toClassScheduleModel(): ClassScheduleModel {
    return ClassScheduleModel(
        currentWeekId = currentWeekId,
        daysOfWeek = daysOfWeek.lessonsOfDayList.map { daysOfWeekDto ->
            daysOfWeekDto.toDayOfWeekModel()
        }.toList()
    )
}