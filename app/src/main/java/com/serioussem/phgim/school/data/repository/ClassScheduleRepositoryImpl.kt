package com.serioussem.phgim.school.data.repository
import com.serioussem.phgim.school.data.jsoup.JsoupParser
import com.serioussem.phgim.school.data.mapper.toClassSchedule
import com.serioussem.phgim.school.data.mapper.toClassScheduleEntity
import com.serioussem.phgim.school.data.retrofit.RetrofitService
import com.serioussem.phgim.school.data.room.dao.ClassScheduleDao
import com.serioussem.phgim.school.data.storage.LocalStorage
import com.serioussem.phgim.school.domain.model.ClassSchedule
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import com.serioussem.phgim.school.domain.util.Result
import com.serioussem.phgim.school.utils.ActionOnWeek.NEXT_WEEK
import com.serioussem.phgim.school.utils.ActionOnWeek.PREVIOUS_WEEK
import com.serioussem.phgim.school.utils.LocalStorageKeys.PUPIL_ID
import com.serioussem.phgim.school.utils.LocalStorageKeys.QUARTER_ID
import com.serioussem.phgim.school.utils.LocalStorageKeys.WEEK_ID
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ClassScheduleRepositoryImpl @Inject constructor(
    private val service: RetrofitService,
    private val jsoupParser: JsoupParser,
    private val storage: LocalStorage,
    private val classScheduleDao: ClassScheduleDao
) : ClassScheduleRepository {
    override suspend fun loadAndSaveClassSchedule(): Result<Boolean> {
        return try {
            getCurrentWeek()
            val currentWeekId = storage.loadData<String>(WEEK_ID, defaultValue = "")
            val classScheduleHtml = getClassSchedulePageHtml(currentWeekId)
            val classSchedule = jsoupParser.parseClassSchedule(
                currentWeek = currentWeekId,
                classScheduleHtml = classScheduleHtml
            ).toClassScheduleEntity()
            classScheduleDao.insertClassSchedule(classSchedule)
            Result.Success(data = true)
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Fetch ClassSchedule error")
        }
    }

    override suspend fun fetchCurrentWeekClassSchedule(): Result<ClassSchedule> {
        return try {
            getCurrentWeek()
            val currentWeekId = storage.loadData<String>(WEEK_ID, defaultValue = "")
            val classScheduleHtml = getClassSchedulePageHtml(currentWeekId)
            val classSchedule = jsoupParser.parseClassSchedule(
                currentWeek = currentWeekId,
                classScheduleHtml = classScheduleHtml
            ).toClassSchedule()
            Result.Success(data = classSchedule)
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Fetch ClassSchedule error")
        }
    }

    override suspend fun fetchNextWeekClassSchedule(): Result<ClassSchedule> {
        return try {
            val nextWeekId = changeWeekId(NEXT_WEEK)
            val classScheduleHtml = getClassSchedulePageHtml(nextWeekId)
            val classSchedule = jsoupParser.parseClassSchedule(
                currentWeek = nextWeekId,
                classScheduleHtml = classScheduleHtml
            ).toClassSchedule()
            Result.Success(data = classSchedule)
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Fetch ClassSchedule error")
        }
    }

    override suspend fun fetchPreviousWeekClassSchedule(): Result<ClassSchedule> {
        return try {
            val previousWeekId = changeWeekId(PREVIOUS_WEEK)
            val classScheduleHtml = getClassSchedulePageHtml(previousWeekId)
            val classSchedule = jsoupParser.parseClassSchedule(
                currentWeek = previousWeekId,
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

    private suspend fun getCurrentWeek() {
        try {
            getPupilId()
            val pupilId = storage.loadData<String>(PUPIL_ID, defaultValue = "")
            var quarterId = storage.loadData<String>(QUARTER_ID, defaultValue = "")
            if (quarterId.isEmpty()) {
                getClassScheduleQuarterId()
                quarterId = storage.loadData(QUARTER_ID, defaultValue = "")
            }
            val endpointResponse =
                service.getClassScheduleCurrentWeekPage(pupilId = pupilId, quarterId = quarterId)
            val weekId = jsoupParser.parseWeek(endpointResponse)
            storage.saveData(WEEK_ID, weekId)
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun getClassScheduleEndpoint(weekId: String): String {
        try {
            getCurrentWeek()
            val pupilId = storage.loadData<String>(PUPIL_ID, defaultValue = "")
            val quarterId = storage.loadData<String>(QUARTER_ID, defaultValue = "")
            return "pupil/$pupilId/dnevnik/quarter/$quarterId/week/$weekId"
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun getClassSchedulePageHtml(weekId: String): String {
        return try {
            val endpoint = getClassScheduleEndpoint(weekId)
            val pageHtml = service.getClassScheduleCurrentPage(endpoint).body().toString()
            pageHtml
        } catch (e: Exception) {
            throw e
        }
    }

    private fun changeWeekId(actionOnWeek: String): String {
        val currentWeekId = storage.loadData<String>(WEEK_ID, defaultValue = "")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(currentWeekId, formatter)
        return if (actionOnWeek == NEXT_WEEK) {
            val newDate = date.plusWeeks(1)
            val weekId = newDate.format(formatter)
            storage.saveData(WEEK_ID, weekId)
            weekId
        } else {
            val newDate = date.minusWeeks(1)
            val weekId = newDate.format(formatter)
            storage.saveData(WEEK_ID, weekId)
            weekId
        }
    }
}