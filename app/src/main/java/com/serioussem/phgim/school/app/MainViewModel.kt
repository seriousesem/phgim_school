package com.serioussem.phgim.school.app

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.serioussem.phgim.school.data.worker.AppWorker
import com.serioussem.phgim.school.presentation.ui.components.AdMobInterstitial
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val adMobInterstitial: AdMobInterstitial
) : ViewModel() {

    companion object {
        const val CHECK_CLASS_SCHEDULE_UPDATE = "check_class_schedule_update"
    }

    init {
        adMobInterstitial.loadAd()
    }

    fun showAddMob(activity: ComponentActivity) {
        adMobInterstitial.showAdd(activity)
    }

    fun startAppWorker(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val myWorkRequest = OneTimeWorkRequestBuilder<AppWorker>()
            .addTag(CHECK_CLASS_SCHEDULE_UPDATE)
            .setConstraints(constraints)
            .setInitialDelay(1, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context).enqueue(myWorkRequest)
    }
}