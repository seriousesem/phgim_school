package com.serioussem.phgim.school.presentation.ui.screens.lesson

import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.serioussem.phgim.school.core.BaseViewModel
import com.serioussem.phgim.school.domain.core.Result
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import com.serioussem.phgim.school.presentation.navigation.NavigationArgumentsKEY.CURRENT_WEEK_ID_KEY
import com.serioussem.phgim.school.presentation.navigation.NavigationArgumentsKEY.DAY_INDEX_KEY
import com.serioussem.phgim.school.presentation.navigation.NavigationArgumentsKEY.LESSON_INDEX_KEY
import com.serioussem.phgim.school.utils.MapKeys.ANNOTATED_STRING_MAP_KEY
import com.serioussem.phgim.school.utils.MapKeys.OFFSET_MAP_KEY
import com.serioussem.phgim.school.utils.MapKeys.URI_HANDLER_MAP_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ClassScheduleRepository,
) : BaseViewModel<LessonScreenContract.Event, LessonScreenContract.State>() {

    private val dayIndex: Int = checkNotNull(savedStateHandle[DAY_INDEX_KEY])
    private val lessonIndex: Int = checkNotNull(savedStateHandle[LESSON_INDEX_KEY])
    private val currentWeekId: String = checkNotNull(savedStateHandle[CURRENT_WEEK_ID_KEY])

    init {
        fetchLesson()
    }

    override fun setInitialState(): LessonScreenContract.State {
        return LessonScreenContract.State(
            lessonName = "",
            homeWork = "",
            hyperlinks = listOf(),
            isLoading = true,
            error = null
        )
    }

    override fun <T> setEvent(event: LessonScreenContract.Event, data: T) {
        when (event) {
            LessonScreenContract.Event.CLOSE_DIALOG -> {
                closeDialog()
            }

            LessonScreenContract.Event.OPEN_LINK -> {
                val dataMap = data as Map<*, *>
                val annotatedString = dataMap[ANNOTATED_STRING_MAP_KEY] as AnnotatedString
                val offset = dataMap[OFFSET_MAP_KEY] as Int
                val uriHandler = dataMap[URI_HANDLER_MAP_KEY] as UriHandler
                openLink(
                    annotatedString = annotatedString,
                    offset = offset,
                    uriHandler = uriHandler
                )
            }
        }
    }

    private fun fetchLesson() {
        setState {
            copy(
                lessonName = this.lessonName,
                homeWork = this.homeWork,
                hyperlinks = this.hyperlinks,
                isLoading = true,
                error = null
            )
        }
        try {
            viewModelScope.launch {
                try {
                    when (val responseResult =
                        repository.fetchClassScheduleByCurrentWeekId(currentWeekId = currentWeekId)) {

                        is Result.Success -> {
                            val classSchedule = responseResult.data
                            val lessonModel =
                                classSchedule?.daysOfWeek?.get(dayIndex)?.lessonsOfDay?.get(
                                    lessonIndex
                                )
                            val hyperlinks = getHyperLinks(lessonModel?.homeWork ?: "")
                            setState {
                                copy(
                                    lessonName = lessonModel?.lessonName ?: "",
                                    homeWork = lessonModel?.homeWork ?: "",
                                    hyperlinks = hyperlinks,
                                    isLoading = false,
                                    error = null
                                )
                            }
                        }

                        is Result.Error -> {
                            setState {
                                copy(
                                    lessonName = this.lessonName,
                                    homeWork = this.homeWork,
                                    hyperlinks = this.hyperlinks,
                                    isLoading = false,
                                    error = responseResult.message
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    setState {
                        copy(
                            lessonName = this.lessonName,
                            homeWork = this.homeWork,
                            hyperlinks = this.hyperlinks,
                            isLoading = false,
                            error = e.message
                        )
                    }
                }
            }
        } catch (e: Exception) {
            setState {
                copy(
                    lessonName = this.lessonName,
                    homeWork = this.homeWork,
                    hyperlinks = this.hyperlinks,
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun getHyperLinks(homeWork: String): List<String> {
        try {
            val hyperlinkPattern = """http[s]?://[^\s)\]]+""".toRegex()
            val matches = hyperlinkPattern.findAll(homeWork)
            return matches.map { matchResult ->
                matchResult.value
            }.toList()
        } catch (e: Exception) {
            throw e
        }
    }

    private fun closeDialog() {
        setState {
            copy(
                lessonName = this.lessonName,
                homeWork = this.homeWork,
                hyperlinks = this.hyperlinks,
                isLoading = false,
                error = null
            )
        }
    }

    private fun openLink(annotatedString: AnnotatedString, offset: Int, uriHandler: UriHandler) {
        annotatedString
            .getStringAnnotations("URL", offset, offset)
            .firstOrNull()?.let { stringAnnotation ->
                uriHandler.openUri(stringAnnotation.item)
            }
    }

}