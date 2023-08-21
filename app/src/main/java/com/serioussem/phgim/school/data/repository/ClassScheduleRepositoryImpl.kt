package com.serioussem.phgim.school.data.repository

import com.serioussem.phgim.school.data.jsoup.JsoupParser
import com.serioussem.phgim.school.data.mapper.toClassSchedule
import com.serioussem.phgim.school.data.retrofit.RetrofitService
import com.serioussem.phgim.school.data.storage.LocalStorage
import com.serioussem.phgim.school.domain.model.ClassSchedule
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import com.serioussem.phgim.school.domain.util.Result
import com.serioussem.phgim.school.utils.EndpointKeys.PUPIL_ID
import com.serioussem.phgim.school.utils.EndpointKeys.QUARTER_ID
import com.serioussem.phgim.school.utils.EndpointKeys.WEEK_ID
import javax.inject.Inject

class ClassScheduleRepositoryImpl @Inject constructor(
    private val service: RetrofitService,
    private val jsoupParser: JsoupParser,
    private val storage: LocalStorage
) : ClassScheduleRepository {
    override suspend fun fetchClassSchedule(currentWeek: String): Result<ClassSchedule> {
        return try {
            val classScheduleHtml = getClassSchedulePageHtml()
            val classSchedule = jsoupParser.parseClassSchedule(
                currentWeek = currentWeek,
                classScheduleHtml = classScheduleHtml
            ).toClassSchedule()
            Result.Success(data = classSchedule)
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Fetch ClassSchedule error")
        }
    }

    companion object {
        const val USER_NAME = "JuliaNabok"
        const val PASSWORD = "qw1234"
//        const val USER_NAME = "Mariana-Nabok"
//        const val PASSWORD = "Nabok2009"
    }

    private suspend fun getToken(): String {
        try {
            val tokenResponse = service.getLoginPage().body() ?: ""
            return jsoupParser.parseToken(tokenResponse)
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun getPupilId() {
        try {
            val token = getToken()
            val endpointResponse = service.getUserPage(USER_NAME, PASSWORD, token)
            val pupilId = jsoupParser.parsePupilId(endpointResponse)
            storage.saveData(PUPIL_ID, pupilId)
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun getClassScheduleQuarterId() {
        try {
            val pupilId = storage.loadData<String>(PUPIL_ID, defaultValue = "")
            val endpointResponse = service.getClassScheduleQuarterPage(pupilId)
            val quarterId = jsoupParser.parseQuarter(endpointResponse)
            storage.saveData(QUARTER_ID, quarterId)
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun getClassScheduleWeek() {
        try {
            getPupilId()
            val pupilId = storage.loadData<String>(PUPIL_ID, defaultValue = "")
            var quarterId = storage.loadData<String>(QUARTER_ID, defaultValue = "")
            if (quarterId.isEmpty()) {
                getClassScheduleQuarterId()
                quarterId = storage.loadData(QUARTER_ID, defaultValue = "")
            }
            val endpointResponse =
                service.getClassScheduleWeekPage(pupilId = pupilId, quarterId = quarterId)
            val weekId = jsoupParser.parseWeek(endpointResponse)
            storage.saveData(WEEK_ID, weekId)
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun getClassScheduleEndpoint(): String {
        try {
            getClassScheduleWeek()
            val pupilId = storage.loadData<String>(PUPIL_ID, defaultValue = "")
            val quarterId = storage.loadData<String>(QUARTER_ID, defaultValue = "")
            val weekId = storage.loadData<String>(WEEK_ID, defaultValue = "")
            return "/pupil/$pupilId/dnevnik/quarter/$quarterId/week/$weekId"
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun getClassSchedulePageHtml(): String {
        return try {
            val endpoint = getClassScheduleEndpoint()
            service.getClassScheduleCurrentPage(endpoint).body().toString()
        } catch (e: Exception) {
            throw e
        }
    }
}