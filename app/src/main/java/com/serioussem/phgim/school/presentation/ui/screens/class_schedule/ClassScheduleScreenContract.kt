package com.serioussem.phgim.school.presentation.ui.screens.class_schedule
import com.serioussem.phgim.school.core.ViewEvent
import com.serioussem.phgim.school.core.ViewState
import com.serioussem.phgim.school.domain.model.DayOfWeek

class ClassScheduleScreenContract {
    enum class Event : ViewEvent {
       PREVIOUS_WEEK, NEXT_WEEK, REFRESH
    }
    data class State(
        val daysOfWeek: List<DayOfWeek> = listOf(),
        val currentWeek: String = "",
        val currentDayIndex: Int = 0,
        val isLoading: Boolean = false,
        val error: String? = null
    ) : ViewState


}