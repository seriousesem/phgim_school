package com.serioussem.phgim.school.data.mapper

import com.serioussem.phgim.school.data.model.ClassScheduleDataModel
import com.serioussem.phgim.school.data.model.LessonDataModel
import com.serioussem.phgim.school.data.model.LessonsOfDayDataModel
import com.serioussem.phgim.school.data.room.entity.ClassScheduleEntity
import com.serioussem.phgim.school.data.room.entity.DaysOfWeek
import com.serioussem.phgim.school.domain.model.ClassSchedule
import com.serioussem.phgim.school.domain.model.DayOfWeek
import com.serioussem.phgim.school.domain.model.Lesson

fun ClassScheduleDataModel.toClassScheduleEntity(): ClassScheduleEntity {
    return ClassScheduleEntity(
        currentWeek = currentWeek,
        daysOfWeek = DaysOfWeek(lessonsOfDayList = daysOfWeek)
    )
}

fun LessonDataModel.toLesson(): Lesson {
    return Lesson(
        lessonName = lessonName,
        homeWork = homeWork,
        mark = mark
    )
}

fun LessonsOfDayDataModel.toLessonsOfDay(): DayOfWeek {
    return DayOfWeek(
        dayIndex = dayIndex,
        lessonsOfDay = lessonsOfDay.map {
            it.toLesson()
        }.toList()
    )
}

fun ClassScheduleDataModel.toClassSchedule(): ClassSchedule {
    return ClassSchedule(
        currentWeek = currentWeek,
        daysOfWeek = daysOfWeek.map {
            it.toLessonsOfDay()
        }.toList()
    )
}