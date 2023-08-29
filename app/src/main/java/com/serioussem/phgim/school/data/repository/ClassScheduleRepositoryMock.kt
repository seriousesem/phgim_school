package com.serioussem.phgim.school.data.repository

import com.serioussem.phgim.school.data.storage.LocalStorage
import com.serioussem.phgim.school.domain.core.Result
import com.serioussem.phgim.school.domain.model.ClassScheduleModel
import com.serioussem.phgim.school.domain.model.DayOfWeekModel
import com.serioussem.phgim.school.domain.model.LessonModel
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import com.serioussem.phgim.school.utils.ActionOnWeek
import com.serioussem.phgim.school.utils.ActionOnWeek.NEXT_WEEK
import com.serioussem.phgim.school.utils.LocalStorageKeys
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ClassScheduleRepositoryMock @Inject constructor(
    private val storage: LocalStorage,
) : ClassScheduleRepository {
    override suspend fun signIn(login: String, password: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun loadAndSaveClassSchedule(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCurrentWeekClassSchedule(): Result<ClassScheduleModel> {
        return try {
            storage.saveData(LocalStorageKeys.WEEK_ID, "2023-08-28" ?: "")
            Result.Success(
                data = ClassScheduleModel(
                    currentWeekId = "2023-08-28",
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
                                    homeWork = "Вивчити таблицю формули - https://www.youtube.com/watch?v=h61Wqy3qcKg&t=453s&ab_channel=PhilippLackner",
                                    mark = "5"
                                ),
                                LessonModel(
                                    lessonName = "Історія України 8 клас",
                                    homeWork = "Прочитати параграфи 5-8 fdgdfg dfg dfg ddg ddgfg dfg dfgg dfcfg fgdfg df  frtf gh tygf",
                                    mark = "н"
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
        } catch (e: Exception) {
            Result.Error(message = "Помилка завантаження щоденника")
        }
    }

    override suspend fun fetchNextWeekClassSchedule(): Result<ClassScheduleModel> {
        return try {
            val nextWeekId = changeWeekId(ActionOnWeek.NEXT_WEEK)
            Result.Success(
                data = ClassScheduleModel(
                    currentWeekId = nextWeekId,
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
                                    homeWork = "Вивчити таблицю формули - https://www.youtube.com/watch?v=h61Wqy3qcKg&t=453s&ab_channel=PhilippLackner",
                                    mark = "5"
                                ),
                                LessonModel(
                                    lessonName = "Історія України 8 клас",
                                    homeWork = "Прочитати параграфи 5-8 fdgdfg dfg dfg ddg ddgfg dfg dfgg dfcfg fgdfg df  frtf gh tygf",
                                    mark = "н"
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
        } catch (e: Exception) {
            Result.Error(message = "Помилка завантаження щоденника")
        }
    }

    override suspend fun fetchPreviousWeekClassSchedule(): Result<ClassScheduleModel> {
        return try {
            val previousWeekId = changeWeekId(ActionOnWeek.PREVIOUS_WEEK)
            Result.Success(
                data = ClassScheduleModel(
                    currentWeekId = previousWeekId,
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
                                    homeWork = "Вивчити таблицю формули - https://www.youtube.com/watch?v=h61Wqy3qcKg&t=453s&ab_channel=PhilippLackner",
                                    mark = "5"
                                ),
                                LessonModel(
                                    lessonName = "Історія України 8 клас",
                                    homeWork = "Прочитати параграфи 5-8 fdgdfg dfg dfg ddg ddgfg dfg dfgg dfcfg fgdfg df  frtf gh tygf",
                                    mark = "н"
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
        } catch (e: Exception) {
            Result.Error(message = "Помилка завантаження щоденника")
        }
    }

    private fun changeWeekId(actionOnWeek: String): String {
        val currentWeekId = storage.loadData<String>(LocalStorageKeys.WEEK_ID, defaultValue = "")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(currentWeekId, formatter)
        val newDate = if (actionOnWeek == NEXT_WEEK) {
            date.plusWeeks(1)
        } else {
            date.minusWeeks(1)
        }
        val newWeekId = calculateWeekId(newDate, actionOnWeek)
        storage.saveData(LocalStorageKeys.WEEK_ID, newWeekId)
        return newWeekId
    }

    private fun calculateWeekId(date: LocalDate, actionOnWeek: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val nextYear = date.year + 1
        val prevYear = date.year - 1

        val endOfYear = LocalDate.of(date.year, Month.DECEMBER, 31)
        val startOfYear = LocalDate.of(date.year, Month.JANUARY, 1)

        val newDate = if (actionOnWeek == NEXT_WEEK) {
            if (date.isAfter(endOfYear)) {
                LocalDate.of(nextYear, Month.JANUARY, 1)
            } else {
                date
            }
        } else {
            if (date.isBefore(startOfYear)) {
                LocalDate.of(prevYear, Month.DECEMBER, 31)
            } else {
                date
            }
        }
        return newDate.format(formatter)
    }
}