package com.serioussem.phgim.school.data.jsoup

import com.serioussem.phgim.school.data.dto.ClassScheduleDto
import com.serioussem.phgim.school.data.dto.LessonDto
import com.serioussem.phgim.school.data.dto.DaysOfWeekDto
import com.serioussem.phgim.school.utils.URL
import com.serioussem.phgim.school.utils.toJsoupDocument
import org.jsoup.Jsoup
import retrofit2.Response
import javax.inject.Inject

class JsoupParser @Inject constructor(
) {
    fun parseClassSchedule(currentWeek: String, classScheduleHtml: String): ClassScheduleDto {
        return try {
            val classSchedulePageDocument = classScheduleHtml.toJsoupDocument()
            val classScheduleElements = classSchedulePageDocument.select(".db_days")
            val weekTableElements = classScheduleElements.select("tbody")
            val daysOfWeekList = mutableListOf<DaysOfWeekDto>()
            for ((index, day) in weekTableElements.withIndex()) {
                val dayTableElements = day.select("tr")
                val lessonsOfDayList = mutableListOf<LessonDto>()
                for (lesson in dayTableElements) {
                    val lessonName = lesson.select("td.lesson > span").text()
                    val homeWork = lesson.select("td.ht > div.ht-aside").text()
                    val mark = lesson.select("td.mark > div.mark_box").text()
                    lessonsOfDayList.add(
                        LessonDto(
                            lessonName = lessonName,
                            homeWork = homeWork,
                            mark = mark
                        )
                    )
                }
                daysOfWeekList.add(
                    DaysOfWeekDto(
                        dayIndex = index,
                        lessonsOfDay = lessonsOfDayList
                    )
                )
            }
            ClassScheduleDto(currentWeekId = currentWeek, daysOfWeek = daysOfWeekList)
        } catch (e: Exception) {
            throw e
        }
    }

    fun parseToken(tokenResponse: String): String {
        return try {
            val tokenDocument = Jsoup.parse(tokenResponse)
            val tokenElement =
                tokenDocument.selectFirst("input[name=csrfmiddlewaretoken]")
            tokenElement?.attr("value") ?: ""
        } catch (e: Exception) {
            throw e
        }
    }

    fun parsePupilId(pageResponse: Response<String>): String {
        try {
            val pageResponseBody = pageResponse.body() ?: ""
            val pageDocument = pageResponseBody.toJsoupDocument()
            val pageResponseString = pageResponse.toString()

            val urlPattern = Regex("url=(https?://[^,\\s}]+)")
            val urlMatchResult = urlPattern.find(pageResponseString)
            val url = urlMatchResult?.groupValues?.get(1)?.replace(URL.BASE_URL, "") ?: ""

            val idPattern = "/(\\w+)/([\\d]+)".toRegex()
            val idMatchResult = idPattern.find(url)
            val userType = idMatchResult?.groupValues?.get(1) ?: ""

            return if (userType == "pupil") {
                val pupilId = idMatchResult?.groupValues?.get(2) ?: ""
                pupilId
            } else {
                val userTypeElement =
                    pageDocument.getElementsByClass("user_type_1").first()
                val pupilTypeElement = userTypeElement?.attr("href") ?: ""
                val pupilIdMatchResult = idPattern.find(pupilTypeElement)
                val pupilId = pupilIdMatchResult?.groupValues?.get(2) ?: ""
                pupilId
            }
        } catch (e: Exception) {
            throw e
        }
    }

    fun parseQuarterId(pageResponse: Response<String>): String {
        return try {
            val pageResponseBody = pageResponse.body() ?: ""
            val pageDocument = pageResponseBody.toJsoupDocument()
            val aTag = pageDocument.select("a.current").first()
            aTag?.attr("quarter_id") ?: ""
        } catch (e: Exception) {
            throw e
        }
    }

    fun parseWeek(pageResponse: Response<String>): String {
        return try {
            val pageResponseBody = pageResponse.body() ?: ""
            val pageDocument = pageResponseBody.toJsoupDocument()
            val scriptElement = pageDocument.getElementsByTag("script").first()
            val scriptContent = scriptElement?.data() ?: ""
            val currentWeekIDStart =
                scriptContent.indexOf("currentWeekID = '") + "currentWeekID = '".length
            val currentWeekIDEnd = scriptContent.indexOf("'", currentWeekIDStart)
            val currentWeekID = scriptContent.substring(currentWeekIDStart, currentWeekIDEnd)
            currentWeekID
        } catch (e: Exception) {
            throw e
        }
    }
}