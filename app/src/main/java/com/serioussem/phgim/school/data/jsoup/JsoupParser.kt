package com.serioussem.phgim.school.data.jsoup

import com.serioussem.phgim.school.data.model.ClassScheduleDataModel
import com.serioussem.phgim.school.data.model.LessonDataModel
import com.serioussem.phgim.school.data.model.LessonsOfDayDataModel
import com.serioussem.phgim.school.utils.URL
import com.serioussem.phgim.school.utils.toJsoupDocument
import org.jsoup.Jsoup
import retrofit2.Response
import java.util.regex.Pattern
import javax.inject.Inject

class JsoupParser @Inject constructor(
) {
    fun parseClassSchedule(currentWeek: String, classScheduleHtml: String): ClassScheduleDataModel {
        return try {
            val classSchedulePageDocument = classScheduleHtml.toJsoupDocument()
            val classScheduleElements = classSchedulePageDocument.select(".db_days")
            val weekTableElements = classScheduleElements.select("tbody")
            val daysOfWeekList = mutableListOf<LessonsOfDayDataModel>()
            for ((index, day) in weekTableElements.withIndex()) {
                val dayTableElements = day.select("tr")
                val lessonsOfDayList = mutableListOf<LessonDataModel>()
                for (lesson in dayTableElements) {
                    val lessonName = lesson.select("td.lesson > span").text()
                    val homeWork = lesson.select("td.ht > span").text()
                    val mark = lesson.select("td.mark > span").text()
                    lessonsOfDayList.add(
                        LessonDataModel(
                            lessonName = lessonName,
                            homeWork = homeWork,
                            mark = mark
                        )
                    )
                }
                daysOfWeekList.add(
                    LessonsOfDayDataModel(
                        dayIndex = index,
                        lessonsOfDay = lessonsOfDayList
                    )
                )
            }
            ClassScheduleDataModel(currentWeek = currentWeek, daysOfWeek = daysOfWeekList)
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

    fun parsePupilId(endpointResponse: Response<String>): String {
        try {
            val endpointResponseBody = endpointResponse.body() ?: ""
            val pageDocument = endpointResponseBody.toJsoupDocument()
            val endpointResponseString = endpointResponse.toString()

            val endpointPattern = Regex("url=(https?://[^,\\s}]+)")
            val endpointMatchResult = endpointPattern.find(endpointResponseString)
            val endpoint = endpointMatchResult?.groupValues?.get(1)?.replace(URL.BASE_URL, "") ?: ""

            val idPattern = "/(\\w+)/([\\d]+)".toRegex()
            val idMatchResult = idPattern.find(endpoint)
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

    fun parseQuarter(endpointResponse: Response<String>): String {
        return try {
            val endpointResponseBody = endpointResponse.body() ?: ""
            val pageDocument = endpointResponseBody.toJsoupDocument()
            val aTag = pageDocument.select("a.current").first()
            aTag?.attr("quarter_id") ?: ""
        } catch (e: Exception) {
            throw e
        }
    }

    fun parseWeek(endpointResponse: Response<String>): String {
        return try {
            val endpointResponseBody = endpointResponse.body() ?: ""
            val pageDocument = endpointResponseBody.toJsoupDocument()
            val scriptTag = pageDocument.select("script:containsData(currentWeekID)").first()
            val scriptContent = scriptTag?.data()
            val searchPattern = "currentWeekID = '(\\d{4}-\\d{2}-\\d{2})'"
            val pattern = Pattern.compile(searchPattern)
            val matcher = pattern.matcher(scriptContent)
            matcher.group(1)
        } catch (e: Exception) {
            throw e
        }
    }
}