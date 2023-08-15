package com.serioussem.phgim.school.di

import com.serioussem.phgim.school.data.retrofit.RetrofitService
import com.serioussem.phgim.school.utils.URL.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideJavaNetCookieJar(): JavaNetCookieJar{
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        return  JavaNetCookieJar(cookieManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, javaNetCookieJar: JavaNetCookieJar): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            cookieJar(javaNetCookieJar)
        }.build()

    @Provides
    @Singleton
    fun provideRetrofitService(okHttpClient: OkHttpClient): RetrofitService {

        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(ScalarsConverterFactory.create())
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
        }.build()
            .create(RetrofitService::class.java)
    }
}
//cookieJar(object : CookieJar {
//    private var cookies: List<Cookie> = ArrayList()
//    private var sessionId: String? = null
//
//    fun setSessionId(sessionId: String) {
//        this.sessionId = sessionId
//    }
//
//    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
//        this.cookies = cookies
//    }
//
//    override fun loadForRequest(url: HttpUrl): List<Cookie> {
//        val filteredCookies = cookies.filter { it.matches(url) }
//        return filteredCookies
//    }
//})