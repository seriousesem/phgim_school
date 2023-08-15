package com.serioussem.phgim.school.data.retrofit
import com.serioussem.phgim.school.utils.Endpoints.LOGIN
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface RetrofitService {

    @GET(LOGIN)
    suspend fun fetchCsrfToken(): Response<String>


    @FormUrlEncoded
    @POST(LOGIN)
    suspend fun fetchPupilId(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("csrfmiddlewaretoken") csrfToken: String
    ): Response<String>

    @GET("/pupil/{pupilId}/dnevnik/quarter/51086/week/2023-09-25")
    suspend fun fetchJournal(@Path("pupilId") pupilId: String): Response<String>

}