package com.serioussem.phgim.school.presentation.ui.screens.class_schedule

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.serioussem.phgim.school.core.BaseViewModel
import com.serioussem.phgim.school.domain.core.Result
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import com.serioussem.phgim.school.presentation.navigation.Screen
import com.serioussem.phgim.school.utils.DateRangeNavigationIconButtonKey.NEXT_WEEK_KEY
import com.serioussem.phgim.school.utils.DateRangeNavigationIconButtonKey.PREVIOUS_WEEK_KEY
import com.serioussem.phgim.school.utils.MapKeys.DAY_INDEX_MAP_KEY
import com.serioussem.phgim.school.utils.MapKeys.LESSON_INDEX_MAP_KEY
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
    private val repository: ClassScheduleRepository,
) : BaseViewModel<ClassScheduleScreenContract.Event, ClassScheduleScreenContract.State>() {

    init {
        fetchCurrentClassSchedule()
    }

    override fun setInitialState(): ClassScheduleScreenContract.State {
        return ClassScheduleScreenContract.State(
            currentWeekId = "",
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
                val dayIndex = dataMap[DAY_INDEX_MAP_KEY] as Int
                val lessonIndex = dataMap[LESSON_INDEX_MAP_KEY] as Int
                openLessonScreen(
                    navController = navController,
                    dayIndex = dayIndex,
                    lessonIndex = lessonIndex,
                )
            }
        }
    }

    private fun closeDialog() {
        fetchLocalClassSchedule()
        setState {
            copy(
                currentWeekId = this.currentWeekId,
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
                currentWeekId = this.currentWeekId,
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
                try {
                    when (val responseResult = repository.fetchCurrentWeekClassSchedule()) {

                        is Result.Success -> {
                            val classSchedule = responseResult.data
                            val daysOfWeek = classSchedule?.daysOfWeek ?: listOf()

                            val weekDateRange =
                                generateWeekDateRange(classSchedule?.currentWeekId ?: "")
                            val currentDayIndex = getCurrentDayIndex()
                            setState {
                                copy(
                                    currentWeekId = classSchedule?.currentWeekId ?: "",
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

                        is Result.Error -> {
                            setState {
                                val classSchedule = responseResult.data
                                val daysOfWeek = classSchedule?.daysOfWeek ?: listOf()

                                val weekDateRange =
                                    generateWeekDateRange(classSchedule?.currentWeekId ?: "")
                                val currentDayIndex = getCurrentDayIndex()
                                copy(
                                    currentWeekId = classSchedule?.currentWeekId ?: "",
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
                                    error = responseResult.message
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    setState {
                        copy(
                            currentWeekId = this.currentWeekId,
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
        } catch (e: Exception) {
            setState {
                copy(
                    currentWeekId = this.currentWeekId,
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

    private fun fetchPreviousWeekClassSchedule() {
        setState {
            copy(
                currentWeekId = this.currentWeekId,
                daysOfWeek = this.daysOfWeek,
                weekDateRange = this.weekDateRange,
                currentDayIndex = 0,
                isShowNextWeekButton = this.isShowNextWeekButton,
                isShowPreviousWeekButton = this.isShowPreviousWeekButton,
                isLoading = true,
                error = null
            )
        }
        try {
            viewModelScope.launch {
                try {
                    when (val responseResult = repository.fetchPreviousWeekClassSchedule()) {

                        is Result.Success -> {
                            val classSchedule = responseResult.data
                            val daysOfWeek = classSchedule?.daysOfWeek ?: listOf()
                            val weekDateRange =
                                generateWeekDateRange(classSchedule?.currentWeekId ?: "")
                            setState {
                                copy(
                                    currentWeekId = classSchedule?.currentWeekId ?: "",
                                    daysOfWeek = daysOfWeek,
                                    weekDateRange = weekDateRange,
                                    currentDayIndex = 0,
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

                        is Result.Error -> {
                            val classSchedule = responseResult.data
                            val daysOfWeek = classSchedule?.daysOfWeek ?: listOf()
                            val weekDateRange =
                                generateWeekDateRange(classSchedule?.currentWeekId ?: "")
                            setState {
                                copy(
                                    currentWeekId = classSchedule?.currentWeekId ?: "",
                                    daysOfWeek = daysOfWeek,
                                    weekDateRange = weekDateRange,
                                    currentDayIndex = 0,
                                    isShowNextWeekButton = showDateRangeNavigationIconButton(
                                        dateStr = classSchedule?.currentWeekId ?: "",
                                        dateRangeNavigationIconButtonKey = NEXT_WEEK_KEY
                                    ),
                                    isShowPreviousWeekButton = showDateRangeNavigationIconButton(
                                        dateStr = classSchedule?.currentWeekId ?: "",
                                        dateRangeNavigationIconButtonKey = PREVIOUS_WEEK_KEY
                                    ),
                                    isLoading = false,
                                    error = responseResult.message
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    setState {
                        copy(
                            currentWeekId = this.currentWeekId,
                            daysOfWeek = this.daysOfWeek,
                            weekDateRange = this.weekDateRange,
                            currentDayIndex = 0,
                            isShowNextWeekButton = this.isShowNextWeekButton,
                            isShowPreviousWeekButton = this.isShowPreviousWeekButton,
                            isLoading = false,
                            error = e.message
                        )
                    }
                }
            }
        } catch (e: Exception) {
            setState {
                copy(
                    currentWeekId = this.currentWeekId,
                    daysOfWeek = this.daysOfWeek,
                    weekDateRange = this.weekDateRange,
                    currentDayIndex = 0,
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
                currentWeekId = this.currentWeekId,
                daysOfWeek = this.daysOfWeek,
                weekDateRange = this.weekDateRange,
                currentDayIndex = 0,
                isShowNextWeekButton = this.isShowNextWeekButton,
                isShowPreviousWeekButton = this.isShowPreviousWeekButton,
                isLoading = true,
                error = null
            )
        }
        try {
            viewModelScope.launch {
                try {
                    when (val responseResult = repository.fetchNextWeekClassSchedule()) {
                        is Result.Success -> {
                            val classSchedule = responseResult.data
                            val daysOfWeek = classSchedule?.daysOfWeek ?: listOf()
                            val weekDateRange =
                                generateWeekDateRange(classSchedule?.currentWeekId ?: "")
                            setState {
                                copy(
                                    currentWeekId = classSchedule?.currentWeekId ?: "",
                                    daysOfWeek = daysOfWeek,
                                    weekDateRange = weekDateRange,
                                    currentDayIndex = 0,
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

                        is Result.Error -> {
                            val classSchedule = responseResult.data
                            val daysOfWeek = classSchedule?.daysOfWeek ?: listOf()
                            val weekDateRange =
                                generateWeekDateRange(classSchedule?.currentWeekId ?: "")
                            setState {
                                copy(
                                    currentWeekId = classSchedule?.currentWeekId ?: "",
                                    daysOfWeek = daysOfWeek,
                                    weekDateRange = weekDateRange,
                                    currentDayIndex = 0,
                                    isShowNextWeekButton = showDateRangeNavigationIconButton(
                                        dateStr = classSchedule?.currentWeekId ?: "",
                                        dateRangeNavigationIconButtonKey = NEXT_WEEK_KEY
                                    ),
                                    isShowPreviousWeekButton = showDateRangeNavigationIconButton(
                                        dateStr = classSchedule?.currentWeekId ?: "",
                                        dateRangeNavigationIconButtonKey = PREVIOUS_WEEK_KEY
                                    ),
                                    isLoading = false,
                                    error = responseResult.message
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    setState {
                        copy(
                            currentWeekId = this.currentWeekId,
                            daysOfWeek = this.daysOfWeek,
                            weekDateRange = this.weekDateRange,
                            currentDayIndex = 0,
                            isShowNextWeekButton = this.isShowNextWeekButton,
                            isShowPreviousWeekButton = this.isShowPreviousWeekButton,
                            isLoading = false,
                            error = e.message
                        )
                    }
                }
            }
        } catch (e: Exception) {
            setState {
                copy(
                    currentWeekId = this.currentWeekId,
                    daysOfWeek = this.daysOfWeek,
                    weekDateRange = this.weekDateRange,
                    currentDayIndex = 0,
                    isShowNextWeekButton = this.isShowNextWeekButton,
                    isShowPreviousWeekButton = this.isShowPreviousWeekButton,
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun fetchLocalClassSchedule() {
        setState {
            copy(
                currentWeekId = this.currentWeekId,
                daysOfWeek = this.daysOfWeek,
                weekDateRange = this.weekDateRange,
                currentDayIndex = this.currentDayIndex,
                isShowNextWeekButton = this.isShowNextWeekButton,
                isShowPreviousWeekButton = this.isShowPreviousWeekButton,
                isLoading = true,
                error = null
            )
        }
        viewModelScope.launch {
            try {
                when (val responseResult = repository.fetchLocalClassSchedule()) {

                    is Result.Success -> {
                        val classSchedule = responseResult.data
                        val daysOfWeek = classSchedule?.daysOfWeek ?: listOf()

                        val weekDateRange =
                            generateWeekDateRange(classSchedule?.currentWeekId ?: "")
                        val currentDayIndex = getCurrentDayIndex()
                        setState {
                            copy(
                                currentWeekId = classSchedule?.currentWeekId ?: "",
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

                    is Result.Error -> {
                        setState {
                            val classSchedule = responseResult.data
                            val daysOfWeek = classSchedule?.daysOfWeek ?: listOf()

                            val weekDateRange =
                                generateWeekDateRange(classSchedule?.currentWeekId ?: "")
                            val currentDayIndex = getCurrentDayIndex()
                            copy(
                                currentWeekId = classSchedule?.currentWeekId ?: "",
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
                                error = responseResult.message
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                setState {
                    copy(
                        currentWeekId = this.currentWeekId,
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
    }

    private fun generateWeekDateRange(inputDate: String): String {
        return if (inputDate.isNotEmpty()) {
            try {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val currentDate = LocalDate.parse(inputDate, formatter)

                val startOfWeek =
                    currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                val endOfWeek = currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))

                val startOfWeekFormatted =
                    startOfWeek.format(
                        DateTimeFormatter.ofPattern(
                            "d MMMM",
                            java.util.Locale("uk")
                        )
                    )
                val endOfWeekFormatted =
                    endOfWeek.format(DateTimeFormatter.ofPattern("d MMMM", java.util.Locale("uk")))

                "$startOfWeekFormatted - $endOfWeekFormatted"
            } catch (e: Exception) {
                setState {
                    copy(
                        currentWeekId = this.currentWeekId,
                        daysOfWeek = this.daysOfWeek,
                        weekDateRange = this.weekDateRange,
                        currentDayIndex = this.currentDayIndex,
                        isShowNextWeekButton = this.isShowNextWeekButton,
                        isShowPreviousWeekButton = this.isShowPreviousWeekButton,
                        isLoading = false,
                        error = e.message
                    )
                }
                ""
            }
        } else {
            ""
        }
    }

    private fun getCurrentDayIndex(): Int {
        val currentDate = LocalDate.now()
        val dayOfWeek = currentDate.dayOfWeek.value
        return if (dayOfWeek > 5) 0 else dayOfWeek - 1
    }

    private fun openLessonScreen(navController: NavController, dayIndex: Int, lessonIndex: Int) {
        navController.navigate(
            route = Screen.Lesson.putArguments(
                dayIndex = dayIndex,
                lessonIndex = lessonIndex,
            )
        )
    }

    private fun showDateRangeNavigationIconButton(
        dateStr: String,
        dateRangeNavigationIconButtonKey: String
    ): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(dateStr, formatter)

            val currentDate = LocalDate.parse(LocalDate.now().format(formatter))
            val startDate = LocalDate.of(currentDate.year, Month.SEPTEMBER, 1)
            val endDate = LocalDate.of(currentDate.year + 1, Month.MAY, 31)

            when (dateRangeNavigationIconButtonKey) {
                PREVIOUS_WEEK_KEY -> {
                    date.isAfter(startDate)
                }

                NEXT_WEEK_KEY -> {
                    date.isBefore(endDate)
                }

                else -> false
            }
        } catch (e: Exception) {
            setState {
                copy(
                    currentWeekId = this.currentWeekId,
                    daysOfWeek = this.daysOfWeek,
                    weekDateRange = this.weekDateRange,
                    currentDayIndex = this.currentDayIndex,
                    isShowNextWeekButton = this.isShowNextWeekButton,
                    isShowPreviousWeekButton = this.isShowPreviousWeekButton,
                    isLoading = false,
                    error = e.message
                )
            }
            false
        }
    }


}