package com.serioussem.phgim.school.worker

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.serioussem.phgim.school.R
import com.serioussem.phgim.school.app.MainActivity
import com.serioussem.phgim.school.utils.NotificationsKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BackgroundWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                showNotification(
                    "Перевірено оновлення в ${
                        LocalDate.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                    }"
                )
                Result.success()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return super.getForegroundInfo()
    }

    private fun showNotification(notificationMessage: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val activityIntent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val activityPendingIntent = PendingIntent.getActivity(
            applicationContext,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(
            applicationContext,
            NotificationsKeys.CLASS_SCHEDULE_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.splash_screen)
            .setContentTitle(NotificationsKeys.CLASS_SCHEDULE_NOTIFICATION_TITLE)
            .setContentText(notificationMessage)
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }

}