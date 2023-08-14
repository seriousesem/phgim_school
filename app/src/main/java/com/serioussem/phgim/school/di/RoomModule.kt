package com.serioussem.phgim.school.di
import android.content.Context
import androidx.room.Room
import com.serioussem.phgim.school.data.room.PhgimSchoolDataBase
import com.serioussem.phgim.school.data.room.dao.LessonDao
import com.serioussem.phgim.school.utils.RoomNames.DATABASE_NAME
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
    ): PhgimSchoolDataBase = Room.databaseBuilder(
        context,
        PhgimSchoolDataBase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideLessonDao(phgimSchoolDataBase: PhgimSchoolDataBase): LessonDao =
        phgimSchoolDataBase.fetchLessonDao()
}