package com.serioussem.phgim.school.presentation.ui.screens.class_schedule
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.serioussem.phgim.school.core.BaseViewModel
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import com.serioussem.phgim.school.presentation.ui.navigation.ScreensRoute
import com.serioussem.phgim.school.presentation.ui.navigation.ScreensRoute.LESSON_SCREEN
import com.serioussem.phgim.school.presentation.ui.screens.login.LoginScreenContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
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
            isLoading = true,
            error = null
        )
    }

    override fun <T> setEvent(event: ClassScheduleScreenContract.Event, data: T) {
        when (event) {
            ClassScheduleScreenContract.Event.PREVIOUS_WEEK -> {

            }

            ClassScheduleScreenContract.Event.NEXT_WEEK -> {

            }

            ClassScheduleScreenContract.Event.REFRESH -> {

            }

            ClassScheduleScreenContract.Event.CLOSE_DIALOG -> {

            }
            ClassScheduleScreenContract.Event.OPEN_LESSON_SCREEN -> {
                openLessonScreen(data as NavController)
            }

        }
    }

    private fun fetchCurrentClassSchedule() {
        try {
            viewModelScope.launch{
                val classSchedule = repository.fetchCurrentWeekClassSchedule().data
                val daysOfWeek = classSchedule?.daysOfWeek ?: listOf()
                val weekDateRange = generateWeekDateRange(classSchedule?.currentWeekId ?: "")
                val currentDayIndex = getCurrentDayIndex()
                setState {
                    copy(
                        daysOfWeek = daysOfWeek,
                        weekDateRange = weekDateRange,
                        currentDayIndex = currentDayIndex,
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

        val startOfWeekFormatted = startOfWeek.format(DateTimeFormatter.ofPattern("d MMMM", java.util.Locale("uk")))
        val endOfWeekFormatted = endOfWeek.format(DateTimeFormatter.ofPattern("d MMMM", java.util.Locale("uk")))

        return "$startOfWeekFormatted - $endOfWeekFormatted"
    }

    private fun getCurrentDayIndex(): Int {
        val currentDate = LocalDate.now()
        val dayOfWeek = currentDate.dayOfWeek.value
        return dayOfWeek - 1
    }

    private fun openLessonScreen(navController: NavController){
        navController.navigate(LESSON_SCREEN)
    }


}