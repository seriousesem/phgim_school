package com.serioussem.phgim.school.data.repository

import com.serioussem.phgim.school.core.ResponseHandler
import com.serioussem.phgim.school.data.jsoup.JsoupParser
import com.serioussem.phgim.school.data.mapper.toClassScheduleModel
import com.serioussem.phgim.school.data.mapper.toClassScheduleEntity
import com.serioussem.phgim.school.data.retrofit.RetrofitService
import com.serioussem.phgim.school.data.room.dao.ClassScheduleDao
import com.serioussem.phgim.school.data.storage.LocalStorage
import com.serioussem.phgim.school.di.RetrofitModule.provideCookieManager
import com.serioussem.phgim.school.di.RetrofitModule.provideJavaNetCookieJar
import com.serioussem.phgim.school.di.RetrofitModule.provideLoggingInterceptor
import com.serioussem.phgim.school.di.RetrofitModule.provideOkHttpClient
import com.serioussem.phgim.school.di.RetrofitModule.provideRetrofitService
import com.serioussem.phgim.school.domain.model.ClassScheduleModel
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import com.serioussem.phgim.school.domain.core.Result
import com.serioussem.phgim.school.utils.ActionOnWeek.NEXT_WEEK
import com.serioussem.phgim.school.utils.ActionOnWeek.PREVIOUS_WEEK
import com.serioussem.phgim.school.utils.LocalStorageKeys.LOGIN_KEY
import com.serioussem.phgim.school.utils.LocalStorageKeys.PASSWORD_KEY
import com.serioussem.phgim.school.utils.LocalStorageKeys.PUPIL_ID
import com.serioussem.phgim.school.utils.LocalStorageKeys.QUARTER_ID
import com.serioussem.phgim.school.utils.LocalStorageKeys.WEEK_ID
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ClassScheduleRepositoryImpl @Inject constructor(
    private var service: RetrofitService?,
    private val jsoupParser: JsoupParser,
    private val storage: LocalStorage,
    private val classScheduleDao: ClassScheduleDao,
    private val responseHandler: ResponseHandler
) : ClassScheduleRepository {
    private fun provideService() {
        val loggingInterceptor = provideLoggingInterceptor()
        val cookieManager = provideCookieManager()
        val javaNetCookieJar = provideJavaNetCookieJar(cookieManager = cookieManager)
        val okHttpClient = provideOkHttpClient(
            loggingInterceptor = loggingInterceptor,
            javaNetCookieJar = javaNetCookieJar
        )
        service = provideRetrofitService(okHttpClient = okHttpClient)
    }

    override suspend fun signIn(login: String, password: String): Result<Boolean> {
        return try {
            if (service == null) {
                provideService()
            }
            val token = getToken()
            val pageResponse =
                responseHandler.fetch {
                    service?.getUserPage(login, password, token)
                }
            val pupilId = pageResponse.let { jsoupParser.parsePupilId(it) }
            if (pupilId.isEmpty()) {
                service = null
                Result.Error(
                    "Не вдалося увійти, перевірте чи вірно введено логін і пароль",
                    data = false
                )
            } else {
                service = null
                Result.Success(data = true)
            }
        } catch (e: Exception) {
            service = null
            Result.Error(message = e.message.toString(), data = false)
        }
    }

    override suspend fun fetchCurrentWeekClassSchedule(): Result<ClassScheduleModel> {
        return try {
            getCurrentWeek()
            val currentWeekId = storage.loadData<String>(WEEK_ID, defaultValue = "")
            val classScheduleHtml = getClassSchedulePageHtml(currentWeekId)
            val classScheduleDto = jsoupParser.parseClassSchedule(
                currentWeek = currentWeekId,
                classScheduleHtml = classScheduleHtml
            )
            val classScheduleEntity = classScheduleDto.toClassScheduleEntity()
            classScheduleDao.insertClassSchedule(classScheduleEntity)
            val classScheduleModel = classScheduleDto.toClassScheduleModel()
            Result.Success(data = classScheduleModel)
        } catch (e: Exception) {
            Result.Error(message = e.message.toString())
        }
    }

    override suspend fun fetchNextWeekClassSchedule(): Result<ClassScheduleModel> {
        return try {
            val nextWeekId = changeWeekId(NEXT_WEEK)
            val classScheduleHtml = getClassSchedulePageHtml(nextWeekId)
            val classScheduleDto = jsoupParser.parseClassSchedule(
                currentWeek = nextWeekId,
                classScheduleHtml = classScheduleHtml
            )
            val classScheduleEntity = classScheduleDto.toClassScheduleEntity()
            classScheduleDao.insertClassSchedule(classScheduleEntity)
            val classScheduleModel = classScheduleDto.toClassScheduleModel()
            Result.Success(data = classScheduleModel)
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Fetch ClassSchedule error")
        }
    }

    override suspend fun fetchPreviousWeekClassSchedule(): Result<ClassScheduleModel> {
        return try {
            val previousWeekId = changeWeekId(PREVIOUS_WEEK)
            val classScheduleHtml = getClassSchedulePageHtml(previousWeekId)
            val classScheduleDto = jsoupParser.parseClassSchedule(
                currentWeek = previousWeekId,
                classScheduleHtml = classScheduleHtml
            )
            val classScheduleEntity = classScheduleDto.toClassScheduleEntity()
            classScheduleDao.insertClassSchedule(classScheduleEntity)
            val classScheduleModel = classScheduleDto.toClassScheduleModel()
            Result.Success(data = classScheduleModel)
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Fetch ClassSchedule error")
        }
    }

    override suspend fun fetchClassScheduleByCurrentWeekId(currentWeekId: String): Result<ClassScheduleModel> {
        return try {
            val classScheduleEntity =
                classScheduleDao.selectClassScheduleByCurrentWeekId(currentWeekId = currentWeekId)
            val classScheduleModel = classScheduleEntity.toClassScheduleModel()
            Result.Success(data = classScheduleModel)
        } catch (e: Exception) {
            Result.Error(message = e.message ?: "Fetch ClassSchedule error")
        }
    }

    override suspend fun synchronizeClassScheduleData(): Boolean {
        return try {
            getCurrentWeek()
            val currentWeekId = storage.loadData<String>(WEEK_ID, defaultValue = "")
            val classScheduleHtml = getClassSchedulePageHtml(currentWeekId)
            val classScheduleDto = jsoupParser.parseClassSchedule(
                currentWeek = currentWeekId,
                classScheduleHtml = classScheduleHtml
            )
            val remoteClassScheduleEntity = classScheduleDto.toClassScheduleEntity()
            val localClassScheduleEntity =
                classScheduleDao.selectClassScheduleByCurrentWeekId(currentWeekId)
            if (localClassScheduleEntity.daysOfWeek.lessonsOfDayList != remoteClassScheduleEntity.daysOfWeek.lessonsOfDayList
            ) {
                classScheduleDao.insertClassSchedule(remoteClassScheduleEntity)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun getToken(): String {
        return try {
            if (service == null) {
                provideService()
            }
            val responseResult =
                responseHandler.fetch {
                    service?.getLoginPage()
                }
            jsoupParser.parseToken(responseResult.body() ?: "")
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun getPupilId() {
        try {
            val login = storage.loadData<String>(LOGIN_KEY, defaultValue = "")
            val password = storage.loadData<String>(PASSWORD_KEY, defaultValue = "")
            val token = getToken()
            val pageResponse =
                responseHandler.fetch {
                    service?.getUserPage(login, password, token)
                }
            val pupilId = pageResponse.let { jsoupParser.parsePupilId(it) }
            storage.saveData(PUPIL_ID, pupilId)
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun getQuarterId() {
        try {
            val pupilId = storage.loadData<String>(PUPIL_ID, defaultValue = "")
            val pageResponse = responseHandler.fetch {
                service?.getQuarterIdPage(pupilId)
            }
            val quarterId = pageResponse.let { jsoupParser.parseQuarterId(it) }
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
                getQuarterId()
                quarterId = storage.loadData(QUARTER_ID, defaultValue = "")
            }
            val endpointResponse = responseHandler.fetch {
                service?.getClassScheduleCurrentWeekPage(pupilId = pupilId, quarterId = quarterId)
            }
            val weekId = endpointResponse.let { jsoupParser.parseWeek(it) }
            storage.saveData(WEEK_ID, weekId)
        } catch (e: Exception) {
            throw e
        }
    }

    private fun getClassScheduleEndpoint(weekId: String): String {
        try {
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
            val pageResponse = responseHandler.fetch {
                service?.getClassScheduleCurrentPage(endpoint)
            }
            val pageHtml = pageResponse.body().toString()
            pageHtml
        } catch (e: Exception) {
            throw e
        }
    }

    private fun changeWeekId(actionOnWeek: String): String {
        return try {
            val currentWeekId = storage.loadData<String>(WEEK_ID, defaultValue = "")
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(currentWeekId, formatter)
            val newDate = if (actionOnWeek == NEXT_WEEK) {
                date.plusWeeks(1)
            } else {
                date.minusWeeks(1)
            }
            val newWeekId = calculateWeekId(newDate, actionOnWeek)
            storage.saveData(WEEK_ID, newWeekId)
            newWeekId
        } catch (e: Exception) {
            throw e
        }
    }

    private fun calculateWeekId(date: LocalDate, actionOnWeek: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val nextYear = date.year + 1
        val prevYear = date.year - 1

        val endOfYear = LocalDate.of(date.year, Month.DECEMBER, 31)
        val startOfYear = LocalDate.of(date.year, Month.JANUARY, 1)

        val newDate = if (actionOnWeek == NEXT_WEEK) {
            if (date.isAfter(endOfYear)) {
//                storage.saveData(QUARTER_ID, "")
                LocalDate.of(nextYear, Month.JANUARY, 1)
            } else {
                date
            }
        } else {
            if (date.isBefore(startOfYear)) {
                LocalDate.of(prevYear, Month.DECEMBER, 31)
            } else {
                date
            }
        }
        return newDate.format(formatter)
    }

}