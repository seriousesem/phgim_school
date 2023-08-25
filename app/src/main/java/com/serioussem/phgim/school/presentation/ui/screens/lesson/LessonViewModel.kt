package com.serioussem.phgim.school.presentation.ui.screens.lesson

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import androidx.lifecycle.viewModelScope
import com.serioussem.phgim.school.core.BaseViewModel
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import com.serioussem.phgim.school.presentation.ui.screens.class_schedule.ClassScheduleScreenContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    private val repository: ClassScheduleRepository
) : BaseViewModel<LessonScreenContract.Event, LessonScreenContract.State>() {

    val homeWorkText = "Что означает Lorem ipsum dolor?\n" +
            "Lorem ipsum dolor sit amet . Графические и типографические операторы хорошо это знают, на самом деле все профессии, имеющие дело со вселенной коммуникации, имеют устойчивую связь с этими словами, но что это такое? Lorem ipsum - это фиктивный текст без всякого смысла.\n" +
            "\n" +"https://www.youtube.com/watch?v=eAbKK7JNxCE&t=3712s&ab_channel=PhilippLackner\n" +
            "\n Это последовательность латинских слов , которые в зависимости от расположения , не составляйте предложения с полным смыслом, но оживляйте тестовый текст, полезный для заполнения пробелов, которые впоследствии будут заняты специальными текстами, составленными профессионалами в области коммуникации.\n" +
            "\n" +"https://phgim.e-schools.info/pupil/968758 \n" +
            "\n Это, безусловно, самый известный текст-заполнитель , даже если есть разные версии, отличные от порядка, в котором повторяются латинские слова.\n" +
            "\n" +
            "Lorem ipsum содержит гарнитуры , которые больше используются, аспект который позволяет получить обзор отображения текста с точки зрения выбора шрифта и d размер шрифта .\n" +
            "\n" +
            "При ссылке на Lorem ipsum используются разные выражения, а именно заполнить текст , фиктивный текст , скрытый текст или текст-заполнитель : короче говоря, его значение также может быть нулевым, но его полезность настолько очевидна, что проходит сквозь века и сопротивляется ироническим и современным версиям, которые пришли с появлением Интернета ».\n" +
            "\n"

    init {
        fetchCurrentClassSchedule()
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
        TODO("Not yet implemented")
    }

    private fun fetchCurrentClassSchedule() {
        val hyperlinks = getHyperLinks(homeWorkText)
        setState {
            copy(
                lessonName = "Географія",
                homeWork = homeWorkText,
                hyperlinks = hyperlinks,
                isLoading = false,
                error = null
            )
        }
    }

    private fun getHyperLinks(homeWork: String): List<String> {
        try {
            val hyperlinkPattern = """http[s]?://[^\s)\]]+""".toRegex()
            val matches = hyperlinkPattern.findAll(homeWork)

            return matches.map { matchResult ->
                matchResult.value
            }.toList()
        }catch (e: Exception) {
            throw e
        }
    }

    private fun openLinkInAppOrBrowser(context: Context, url: String) {
        val intent = if (url.contains("youtube.com")) {
            val videoId = url.substringAfterLast("v=").substringBefore("&")
            Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
        } else {
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        }

        context.startActivity(intent)
    }

}