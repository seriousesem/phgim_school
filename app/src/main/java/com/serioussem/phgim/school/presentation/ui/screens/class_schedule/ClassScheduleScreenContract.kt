package com.serioussem.phgim.school.presentation.ui.screens.class_schedule
import com.serioussem.phgim.school.core.ViewEvent
import com.serioussem.phgim.school.core.ViewState
import com.serioussem.phgim.school.domain.model.DayOfWeekModel

class ClassScheduleScreenContract {
    enum class Event : ViewEvent {
       PREVIOUS_WEEK, NEXT_WEEK, REFRESH, CLOSE_DIALOG, OPEN_LESSON_SCREEN
    }
    data class State(
        val daysOfWeek: List<DayOfWeekModel> = listOf(),
        val weekDateRange: String = "",
        val currentDayIndex: Int = 0,
        val isShowNextWeekButton: Boolean = false,
        val isShowPreviousWeekButton: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null
    ) : ViewState


}