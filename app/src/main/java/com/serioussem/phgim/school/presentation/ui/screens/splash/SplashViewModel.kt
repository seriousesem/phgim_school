package com.serioussem.phgim.school.presentation.ui.screens.splash

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.serioussem.phgim.school.data.storage.LocalStorage
import com.serioussem.phgim.school.presentation.navigation.Screen
import com.serioussem.phgim.school.worker.BackgroundWorker
import com.serioussem.phgim.school.utils.LocalStorageKeys.LOGIN_KEY
import com.serioussem.phgim.school.utils.LocalStorageKeys.PASSWORD_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit.MINUTES
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val storage: LocalStorage,
) : ViewModel() {

    var loadingProgressState by mutableFloatStateOf(0.0F)
        private set

    private var isAuthorized = false

    companion object {
        const val PROGRESS_DELAY = 35L
        const val CHECK_CLASS_SCHEDULE_UPDATE = "check_class_schedule_update"
    }

    init {
        isAuthorized = checkAuthorization()
    }

    suspend fun navigateToNextScreen(navController: NavController) {
        startLoadingProgress()
        if (!isAuthorized) {
            navController.navigate(Screen.Login.route)
        } else {
            navController.navigate(Screen.ClassSchedule.route)
        }
    }

    private fun checkAuthorization(): Boolean {
        try {
            val login = storage.loadData<String>(LOGIN_KEY, defaultValue = "")
            val password = storage.loadData<String>(PASSWORD_KEY, defaultValue = "")
            return login.isNotEmpty() && password.isNotEmpty()
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun startLoadingProgress() {
        while (loadingProgressState < 1) {
            loadingProgressState += 0.01F
            delay(PROGRESS_DELAY)
        }
    }

    fun startBackgroundWorker(context: Context) {
        if (isAuthorized) {
            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val workRequest = PeriodicWorkRequestBuilder<BackgroundWorker>(
                repeatInterval = 30,
                repeatIntervalTimeUnit = MINUTES
            )
                .setConstraints(constraints)
                .setInitialDelay(30, MINUTES)
                .build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                CHECK_CLASS_SCHEDULE_UPDATE,
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )
        }
    }

}