package com.serioussem.phgim.school.di
import android.content.Context
import androidx.room.Room
import com.serioussem.phgim.school.data.room.db.AppDataBase
import com.serioussem.phgim.school.data.room.dao.ClassScheduleDao
import com.serioussem.phgim.school.utils.RoomConstants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideSpaceScutumDataBase(
        @ApplicationContext context: Context
    ): AppDataBase = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideLessonDao(appDataBase: AppDataBase): ClassScheduleDao =
        appDataBase.fetchClassScheduleDao()
}