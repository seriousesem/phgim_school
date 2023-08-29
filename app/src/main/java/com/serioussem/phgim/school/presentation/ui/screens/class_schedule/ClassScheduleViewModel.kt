package com.serioussem.phgim.school.presentation.ui.screens.class_schedule

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.serioussem.phgim.school.core.BaseViewModel
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import com.serioussem.phgim.school.presentation.ui.navigation.Screen
import com.serioussem.phgim.school.utils.DateRangeNavigationIconButtonKey.NEXT_WEEK_KEY
import com.serioussem.phgim.school.utils.DateRangeNavigationIconButtonKey.PREVIOUS_WEEK_KEY
import com.serioussem.phgim.school.utils.MapKeys
import com.serioussem.phgim.school.utils.MapKeys.NAV_CONTROLLER_MAP_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class ClassScheduleViewModel @Inject constructor(
    private val repository: ClassScheduleRepository
) : BaseViewModel<ClassScheduleScreenContract.Event, ClassScheduleScreenContract.State>() {

    init {
        fetchCurrentClassSchedule()
    }

    override fun setInitialState(): ClassScheduleScreenContract.State {
        return ClassScheduleScreenContract.State(
            daysOfWeek = listOf(),
            weekDateRange = "",
            currentDayIndex = 0,
            isShowNextWeekButton = false,
            isShowPreviousWeekButton = false,
            isLoading = true,
            error = null
        )
    }

    override fun <T> setEvent(event: ClassScheduleScreenContract.Event, data: T) {
        when (event) {
            ClassScheduleScreenContract.Event.PREVIOUS_WEEK -> {
                fetchPreviousWeekClassSchedule()
            }

            ClassScheduleScreenContract.Event.NEXT_WEEK -> {
                fetchNextWeekClassSchedule()
            }

            ClassScheduleScreenContract.Event.REFRESH -> {

            }

            ClassScheduleScreenContract.Event.CLOSE_DIALOG -> {
                closeDialog()
            }

            ClassScheduleScreenContract.Event.OPEN_LESSON_SCREEN -> {
                val dataMap = data as Map<*, *>
                val navController = dataMap[NAV_CONTROLLER_MAP_KEY] as NavController
                val dayIndex = dataMap[MapKeys.DAY_INDEX_MAP_KEY] as Int
                val lessonIndex = dataMap[MapKeys.LESSON_INDEX_MAP_KEY] as Int
                openLessonScreen(
                    navController = navController,
                    dayIndex = dayIndex,
                    lessonIndex = lessonIndex
                )
            }
        }
    }

    private fun closeDialog() {
        setState {
            copy(
                daysOfWeek = this.daysOfWeek,
                weekDateRange = this.weekDateRange,
                currentDayIndex = this.currentDayIndex,
                isShowNextWeekButton = this.isShowNextWeekButton,
                isShowPreviousWeekButton = this.isShowPreviousWeekButton,
                isLoading = false,
                error = null
            )
        }
    }

    private fun fetchCurrentClassSchedule() {
        setState {
            copy(
                daysOfWeek = this.daysOfWeek,
                weekDateRange = this.weekDateRange,
                currentDayIndex = this.currentDayIndex,
                isShowNextWeekButton = this.isShowNextWeekButton,
                isShowPreviousWeekButton = this.isShowPreviousWeekButton,
                isLoading = true,
                error = null
            )
        }
        try {
            viewModelScope.launch {
                val classSchedule = repository.fetchCurrentWeekClassSchedule().data
                val daysOfWeek = classSchedule?.daysOfWeek ?: listOf()
                val weekDateRange = generateWeekDateRange(classSchedule?.currentWeekId ?: "")
                val currentDayIndex = getCurrentDayIndex()
                setState {
                    copy(
                        daysOfWeek = daysOfWeek,
                        weekDateRange = weekDateRange,
                        currentDayIndex = currentDayIndex,
                        isShowNextWeekButton = showDateRangeNavigationIconButton(
                            dateStr = classSchedule?.currentWeekId ?: "",
                            dateRangeNavigationIconButtonKey = NEXT_WEEK_KEY
                        ),
                        isShowPreviousWeekButton = showDateRangeNavigationIconButton(
                            dateStr = classSchedule?.currentWeekId ?: "",
                            dateRangeNavigationIconButtonKey = PREVIOUS_WEEK_KEY
                        ),
                        isLoading = false,
                        error = null
                    )
                }
            }
        } catch (e: Exception) {
            setState {
                copy(
                    daysOfWeek = listOf(),
                    weekDateRange = "",
                    currentDayIndex = 0,
                    isShowNextWeekButton = false,
                    isShowPreviousWeekButton = false,
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun fetchPreviousWeekClassSchedule() {
        setState {
            copy(
                daysOfWeek = this.daysOfWeek,
                weekDateRange = this.weekDateRange,
                currentDayIndex = this.currentDayIndex,
                isShowNextWeekButton = this.isShowNextWeekButton,
                isShowPreviousWeekButton = this.isShowPreviousWeekButton,
                isLoading = true,
                error = null
            )
        }
        try {
            viewModelScope.launch {
                val classSchedule = repository.fetchPreviousWeekClassSchedule().data
                val daysOfWeek = classSchedule?.daysOfWeek ?: listOf()
                val weekDateRange = generateWeekDateRange(classSchedule?.currentWeekId ?: "")
                val currentDayIndex = getCurrentDayIndex()
                setState {
                    copy(
                        daysOfWeek = daysOfWeek,
                        weekDateRange = weekDateRange,
                        currentDayIndex = currentDayIndex,
                        isShowNextWeekButton = showDateRangeNavigationIconButton(
                            dateStr = classSchedule?.currentWeekId ?: "",
                            dateRangeNavigationIconButtonKey = NEXT_WEEK_KEY
                        ),
                        isShowPreviousWeekButton = showDateRangeNavigationIconButton(
                            dateStr = classSchedule?.currentWeekId ?: "",
                            dateRangeNavigationIconButtonKey = PREVIOUS_WEEK_KEY
                        ),
                        isLoading = false,
                        error = null
                    )
                }
            }
        } catch (e: Exception) {
            setState {
                copy(
                    daysOfWeek = this.daysOfWeek,
                    weekDateRange = this.weekDateRange,
                    currentDayIndex = this.currentDayIndex,
                    isShowNextWeekButton = this.isShowNextWeekButton,
                    isShowPreviousWeekButton = this.isShowPreviousWeekButton,
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun fetchNextWeekClassSchedule() {
        setState {
            copy(
                daysOfWeek = this.daysOfWeek,
                weekDateRange = this.weekDateRange,
                currentDayIndex = this.currentDayIndex,
                isShowNextWeekButton = this.isShowNextWeekButton,
                isShowPreviousWeekButton = this.isShowPreviousWeekButton,
                isLoading = true,
                error = null
            )
        }
        try {
            viewModelScope.launch {
                val classSchedule = repository.fetchNextWeekClassSchedule().data
                val daysOfWeek = classSchedule?.daysOfWeek ?: listOf()
                val weekDateRange = generateWeekDateRange(classSchedule?.currentWeekId ?: "")
                val currentDayIndex = getCurrentDayIndex()
                setState {
                    copy(
                        daysOfWeek = daysOfWeek,
                        weekDateRange = weekDateRange,
                        currentDayIndex = currentDayIndex,
                        isShowNextWeekButton = showDateRangeNavigationIconButton(
                            dateStr = classSchedule?.currentWeekId ?: "",
                            dateRangeNavigationIconButtonKey = NEXT_WEEK_KEY
                        ),
                        isShowPreviousWeekButton = showDateRangeNavigationIconButton(
                            dateStr = classSchedule?.currentWeekId ?: "",
                            dateRangeNavigationIconButtonKey = PREVIOUS_WEEK_KEY
                        ),
                        isLoading = false,
                        error = null
                    )
                }
            }
        } catch (e: Exception) {
            setState {
                copy(
                    daysOfWeek = this.daysOfWeek,
                    weekDateRange = this.weekDateRange,
                    currentDayIndex = this.currentDayIndex,
                    isShowNextWeekButton = this.isShowNextWeekButton,
                    isShowPreviousWeekButton = this.isShowPreviousWeekButton,
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun generateWeekDateRange(inputDate: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentDate = LocalDate.parse(inputDate, formatter)

        val startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val endOfWeek = currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))

        val startOfWeekFormatted =
            startOfWeek.format(DateTimeFormatter.ofPattern("d MMMM", java.util.Locale("uk")))
        val endOfWeekFormatted =
            endOfWeek.format(DateTimeFormatter.ofPattern("d MMMM", java.util.Locale("uk")))

        return "$startOfWeekFormatted - $endOfWeekFormatted"
    }

    private fun getCurrentDayIndex(): Int {
        val currentDate = LocalDate.now()
        val dayOfWeek = currentDate.dayOfWeek.value
        return dayOfWeek - 1
    }

    private fun openLessonScreen(navController: NavController, dayIndex: Int, lessonIndex: Int) {
        navController.navigate(
            route = Screen.Lesson.putArguments(
                dayIndex = dayIndex,
                lessonIndex = lessonIndex
            )
        )
    }

    private fun showDateRangeNavigationIconButton(
        dateStr: String,
        dateRangeNavigationIconButtonKey: String
    ): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(dateStr, formatter)

        val currentDate = LocalDate.parse(LocalDate.now().format(formatter))
        val startDate = LocalDate.of(currentDate.year, Month.SEPTEMBER, 1)
        val endDate = LocalDate.of(currentDate.year + 1, Month.MAY, 31)

        return when (dateRangeNavigationIconButtonKey) {
            PREVIOUS_WEEK_KEY -> {
                date.isAfter(startDate)
            }

            NEXT_WEEK_KEY -> {
                date.isBefore(endDate)
            }

            else -> false
        }
    }


}