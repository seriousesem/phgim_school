package com.serioussem.phgim.school.di

import com.serioussem.phgim.school.data.repository.ClassScheduleRepositoryImpl
import com.serioussem.phgim.school.data.repository.ClassScheduleRepositoryMock
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindClassScheduleRepository(
        classScheduleRepositoryImpl: ClassScheduleRepositoryMock
    ): ClassScheduleRepository
}