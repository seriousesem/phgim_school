package com.serioussem.phgim.school.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.serioussem.phgim.school.domain.repository.ClassScheduleRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class AppWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: ClassScheduleRepository
) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        try {

        } catch (e: Exception) {
            return Result.failure()
        }
        return Result.success()
    }
}