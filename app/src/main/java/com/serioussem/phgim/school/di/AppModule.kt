package com.serioussem.phgim.school.di

import com.serioussem.phgim.school.presentation.ui.components.AdMobInterstitial
import android.content.Context
import com.serioussem.phgim.school.core.ResponseHandler
import com.serioussem.phgim.school.data.jsoup.JsoupParser
import com.serioussem.phgim.school.data.repository.ClassScheduleRepositoryImpl
import com.serioussem.phgim.school.data.retrofit.RetrofitService
import com.serioussem.phgim.school.data.room.dao.ClassScheduleDao
import com.serioussem.phgim.school.data.storage.BaseLocalStorage
import com.serioussem.phgim.school.data.storage.LocalStorage
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalStorage(@ApplicationContext context: Context): BaseLocalStorage =
        LocalStorage(context = context)

    @Provides
    @Singleton
    fun provideClassScheduleRepository(
        service: RetrofitService?,
        jsoupParser: JsoupParser,
        storage: LocalStorage,
        classScheduleDao: ClassScheduleDao,
        responseHandler: ResponseHandler
    ): ClassScheduleRepository =
        ClassScheduleRepositoryImpl(
            service = service,
            jsoupParser = jsoupParser,
            storage = storage,
            classScheduleDao = classScheduleDao,
            responseHandler = responseHandler
        )

    @Singleton
    @Provides
    fun provideAdMobInterstitial(
        @ApplicationContext context: Context
    ): AdMobInterstitial = AdMobInterstitial(context)

    @Provides
    @Singleton
    fun provideResponseHandler(
        @ApplicationContext context: Context,
    ): ResponseHandler =
        ResponseHandler(context = context)

}