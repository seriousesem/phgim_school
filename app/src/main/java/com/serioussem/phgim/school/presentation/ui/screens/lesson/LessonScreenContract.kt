package com.serioussem.phgim.school.presentation.ui.screens.lesson

import com.serioussem.phgim.school.core.ViewEvent
import com.serioussem.phgim.school.core.ViewState
import com.serioussem.phgim.school.domain.model.DayOfWeekModel

class LessonScreenContract {
    enum class Event : ViewEvent {
        OPEN_LINK, CLOSE_DIALOG
    }

    data class State(
        val lessonName: String = "",
        val homeWork: String = "",
        val hyperlinks: List<String> = listOf(),
        val isLoading: Boolean = false,
        val error: String? = null
    ) : ViewState


}