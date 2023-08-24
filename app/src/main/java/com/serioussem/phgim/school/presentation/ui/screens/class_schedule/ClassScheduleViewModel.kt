package com.serioussem.phgim.school.presentation.ui.screens.class_schedule
import com.serioussem.phgim.school.core.BaseViewModel
import com.serioussem.phgim.school.domain.model.DayOfWeek
import com.serioussem.phgim.school.domain.model.Lesson
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClassScheduleViewModel @Inject constructor(
    private val repository: ClassScheduleRepository
) : BaseViewModel<ClassScheduleScreenContract.Event, ClassScheduleScreenContract.State>() {
    override fun setInitialState(): ClassScheduleScreenContract.State {

        return ClassScheduleScreenContract.State(
            daysOfWeek = listOf(
                DayOfWeek(
                   dayIndex = 0,
                   lessonsOfDay = listOf(
                       Lesson(
                           lessonName = "Алгебра",
                           homeWork = "Вивчити таблицю множення",
                           mark = "10"
                       ),
                       Lesson(
                           lessonName = "Геометрія",
                           homeWork = "Вивчити таблицю тиореми",
                           mark = "8"
                       ),
                       Lesson(
                           lessonName = "Фізика",
                           homeWork = "Вивчити таблицю формули",
                           mark = "9"
                       ),
                       Lesson(
                           lessonName = "Історія України 8 клас",
                           homeWork = "Прочитати параграфи 5-8 fdgdfg dfg dfg ddg ddgfg dfg dfgg dfcfg fgdfg df  frtf gh tygf",
                           mark = "11"
                       ),
                   )
                ),
                DayOfWeek(
                    dayIndex = 1,
                    lessonsOfDay = listOf(
                        Lesson(
                            lessonName = "Алгебра",
                            homeWork = "Вивчити таблицю множення",
                            mark = "10"
                        ),
                        Lesson(
                            lessonName = "Геометрія",
                            homeWork = "Вивчити таблицю тиореми",
                            mark = "8"
                        ),
                        Lesson(
                            lessonName = "Фізика",
                            homeWork = "Вивчити таблицю формули",
                            mark = "9"
                        ),
                        Lesson(
                            lessonName = "Історія",
                            homeWork = "Прочитати параграфи 5-8",
                            mark = "11"
                        ),
                    )
                ),
                DayOfWeek(
                    dayIndex = 2,
                    lessonsOfDay = listOf(
                        Lesson(
                            lessonName = "Алгебра",
                            homeWork = "Вивчити таблицю множення",
                            mark = "10"
                        ),
                        Lesson(
                            lessonName = "Геометрія",
                            homeWork = "Вивчити таблицю тиореми",
                            mark = "8"
                        ),
                        Lesson(
                            lessonName = "Фізика",
                            homeWork = "Вивчити таблицю формули",
                            mark = "9"
                        ),
                        Lesson(
                            lessonName = "Історія",
                            homeWork = "Прочитати параграфи 5-8",
                            mark = "11"
                        ),
                    )
                ),
                DayOfWeek(
                    dayIndex = 3,
                    lessonsOfDay = listOf(
                        Lesson(
                            lessonName = "Алгебра",
                            homeWork = "Вивчити таблицю множення",
                            mark = "10"
                        ),
                        Lesson(
                            lessonName = "Геометрія",
                            homeWork = "Вивчити таблицю тиореми",
                            mark = "8"
                        ),
                        Lesson(
                            lessonName = "Фізика",
                            homeWork = "Вивчити таблицю формули",
                            mark = "9"
                        ),
                        Lesson(
                            lessonName = "Історія",
                            homeWork = "Прочитати параграфи 5-8",
                            mark = "11"
                        ),
                    )
                ),
                DayOfWeek(
                    dayIndex = 4,
                    lessonsOfDay = listOf(
                        Lesson(
                            lessonName = "Алгебра",
                            homeWork = "Вивчити таблицю множення",
                            mark = "10"
                        ),
                        Lesson(
                            lessonName = "Геометрія",
                            homeWork = "Вивчити таблицю тиореми",
                            mark = "8"
                        ),
                        Lesson(
                            lessonName = "Фізика",
                            homeWork = "Вивчити таблицю формули",
                            mark = "9"
                        ),
                        Lesson(
                            lessonName = "Історія",
                            homeWork = "Прочитати параграфи 5-8",
                            mark = "11"
                        ),
                    )
                ),
                DayOfWeek(
                    dayIndex = 5,
                    lessonsOfDay = listOf(
                        Lesson(
                            lessonName = "Алгебра",
                            homeWork = "Вивчити таблицю множення",
                            mark = "10"
                        ),
                        Lesson(
                            lessonName = "Геометрія",
                            homeWork = "Вивчити таблицю тиореми",
                            mark = "8"
                        ),
                        Lesson(
                            lessonName = "Фізика",
                            homeWork = "Вивчити таблицю формули",
                            mark = "9"
                        ),
                        Lesson(
                            lessonName = "Історія",
                            homeWork = "Прочитати параграфи 5-8",
                            mark = "11"
                        ),
                    )
                ),
            ),
            currentWeek = "28 серпня - 3 вересня",
            currentDayIndex = 0,
            isLoading = false,
            error = null
        )
    }

    override fun <T> setEvent(event: ClassScheduleScreenContract.Event, data: T) {
        TODO("Not yet implemented")
    }


}