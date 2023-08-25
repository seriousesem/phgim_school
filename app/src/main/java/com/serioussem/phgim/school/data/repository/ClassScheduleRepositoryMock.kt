package com.serioussem.phgim.school.data.repository

import com.serioussem.phgim.school.domain.core.Result
import com.serioussem.phgim.school.domain.model.ClassScheduleModel
import com.serioussem.phgim.school.domain.model.DayOfWeekModel
import com.serioussem.phgim.school.domain.model.LessonModel
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import javax.inject.Inject

class ClassScheduleRepositoryMock @Inject constructor(): ClassScheduleRepository {
    override suspend fun signIn(login: String, password: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun loadAndSaveClassSchedule(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCurrentWeekClassSchedule(): Result<ClassScheduleModel> {
       return try {
           Result.Success(
               data = ClassScheduleModel(
                   currentWeekId = "2023-09-04",
                   daysOfWeek = listOf(
                       DayOfWeekModel(
                           dayIndex = 0,
                           lessonsOfDay = listOf(
                               LessonModel(
                                   lessonName = "Алгебра",
                                   homeWork = "Вивчити таблицю множення",
                                   mark = "10"
                               ),
                               LessonModel(
                                   lessonName = "Геометрія",
                                   homeWork = "Вивчити таблицю тиореми",
                                   mark = "8"
                               ),
                               LessonModel(
                                   lessonName = "Фізика",
                                   homeWork = "Вивчити таблицю формули",
                                   mark = "9"
                               ),
                               LessonModel(
                                   lessonName = "Історія України 8 клас",
                                   homeWork = "Прочитати параграфи 5-8 fdgdfg dfg dfg ddg ddgfg dfg dfgg dfcfg fgdfg df  frtf gh tygf",
                                   mark = "11"
                               ),
                           )
                       ),
                       DayOfWeekModel(
                           dayIndex = 1,
                           lessonsOfDay = listOf(
                               LessonModel(
                                   lessonName = "Алгебра",
                                   homeWork = "Вивчити таблицю множення",
                                   mark = "10"
                               ),
                               LessonModel(
                                   lessonName = "Геометрія",
                                   homeWork = "Вивчити таблицю тиореми",
                                   mark = "8"
                               ),
                               LessonModel(
                                   lessonName = "Фізика",
                                   homeWork = "Вивчити таблицю формули",
                                   mark = "9"
                               ),
                               LessonModel(
                                   lessonName = "Історія",
                                   homeWork = "Прочитати параграфи 5-8",
                                   mark = "11"
                               ),
                           )
                       ),
                       DayOfWeekModel(
                           dayIndex = 2,
                           lessonsOfDay = listOf(
                               LessonModel(
                                   lessonName = "Алгебра",
                                   homeWork = "Вивчити таблицю множення",
                                   mark = "10"
                               ),
                               LessonModel(
                                   lessonName = "Геометрія",
                                   homeWork = "Вивчити таблицю тиореми",
                                   mark = "8"
                               ),
                               LessonModel(
                                   lessonName = "Фізика",
                                   homeWork = "Вивчити таблицю формули",
                                   mark = "9"
                               ),
                               LessonModel(
                                   lessonName = "Історія",
                                   homeWork = "Прочитати параграфи 5-8",
                                   mark = "11"
                               ),
                           )
                       ),
                       DayOfWeekModel(
                           dayIndex = 3,
                           lessonsOfDay = listOf(
                               LessonModel(
                                   lessonName = "Алгебра",
                                   homeWork = "Вивчити таблицю множення",
                                   mark = "10"
                               ),
                               LessonModel(
                                   lessonName = "Геометрія",
                                   homeWork = "Вивчити таблицю тиореми",
                                   mark = "8"
                               ),
                               LessonModel(
                                   lessonName = "Фізика",
                                   homeWork = "Вивчити таблицю формули",
                                   mark = "9"
                               ),
                               LessonModel(
                                   lessonName = "Історія",
                                   homeWork = "Прочитати параграфи 5-8",
                                   mark = "11"
                               ),
                           )
                       ),
                       DayOfWeekModel(
                           dayIndex = 4,
                           lessonsOfDay = listOf(
                               LessonModel(
                                   lessonName = "Алгебра",
                                   homeWork = "Вивчити таблицю множення",
                                   mark = "10"
                               ),
                               LessonModel(
                                   lessonName = "Геометрія",
                                   homeWork = "Вивчити таблицю тиореми",
                                   mark = "8"
                               ),
                               LessonModel(
                                   lessonName = "Фізика",
                                   homeWork = "Вивчити таблицю формули",
                                   mark = "9"
                               ),
                               LessonModel(
                                   lessonName = "Історія",
                                   homeWork = "Прочитати параграфи 5-8",
                                   mark = "11"
                               ),
                           )
                       ),
                       DayOfWeekModel(
                           dayIndex = 5,
                           lessonsOfDay = listOf(
                               LessonModel(
                                   lessonName = "Алгебра",
                                   homeWork = "Вивчити таблицю множення",
                                   mark = "10"
                               ),
                               LessonModel(
                                   lessonName = "Геометрія",
                                   homeWork = "Вивчити таблицю тиореми",
                                   mark = "8"
                               ),
                               LessonModel(
                                   lessonName = "Фізика",
                                   homeWork = "Вивчити таблицю формули",
                                   mark = "9"
                               ),
                               LessonModel(
                                   lessonName = "Історія",
                                   homeWork = "Прочитати параграфи 5-8",
                                   mark = "11"
                               ),
                           )
                       ),
                   )
               )
           )
       }catch(e: Exception) {
           Result.Error(message = "Помилка завантаження щоденника")
       }
    }

    override suspend fun fetchNextWeekClassSchedule(): Result<ClassScheduleModel> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPreviousWeekClassSchedule(): Result<ClassScheduleModel> {
        TODO("Not yet implemented")
    }
}