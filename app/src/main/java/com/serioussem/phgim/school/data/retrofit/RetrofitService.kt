package com.serioussem.phgim.school.data.retrofit
import com.serioussem.phgim.school.utils.Endpoints.LOGIN
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface RetrofitService {

    @GET(LOGIN)
    suspend fun fetchCsrfToken(): Response<String>


    @FormUrlEncoded
    @POST(LOGIN)
    suspend fun fetchEndPoint(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("csrfmiddlewaretoken") csrfToken: String
    ): Response<String>


}