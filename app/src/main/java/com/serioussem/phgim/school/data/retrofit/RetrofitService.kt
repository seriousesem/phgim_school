package com.serioussem.phgim.school.data.retrofit
import com.serioussem.phgim.school.utils.Endpoints.CLASS_SCHEDULE_ENDPOINT
import com.serioussem.phgim.school.utils.Endpoints.LOGIN_ENDPOINT
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface RetrofitService {

    @GET(LOGIN_ENDPOINT)
    suspend fun getLoginPage(): Response<String>


    @FormUrlEncoded
    @POST(LOGIN_ENDPOINT)
    suspend fun getUserPage(
        @Field("username") login: String,
        @Field("password") password: String,
        @Field("csrfmiddlewaretoken") csrfToken: String
    ): Response<String>

    @GET(CLASS_SCHEDULE_ENDPOINT)
    suspend fun getClassScheduleQuarterPage(@Path("pupilId") pupilId: String): Response<String>

    @GET("$CLASS_SCHEDULE_ENDPOINT/quarter/{quarter}")
    suspend fun getClassScheduleCurrentWeekPage(@Path("pupilId") pupilId: String, @Path("quarter") quarterId: String): Response<String>

    @GET("/{endpoint}")
    suspend fun getClassScheduleCurrentPage(@Path("endpoint") endpoint: String): Response<String>

}