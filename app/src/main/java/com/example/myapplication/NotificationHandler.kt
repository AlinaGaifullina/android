package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationHandler(private val context: Context) {

    private var notificationManager: NotificationManager? = null
    private var notificationBuilder:NotificationCompat.Builder? = null

    fun getNotificationBuilder(service: AppService, title: String, content: String): NotificationCompat.Builder? {
        val notificationPendingIntent = PendingIntent.getActivity(
            service, NOTIFICATION_REQUEST_CODE, Intent(context, MainActivity::class.java), PENDING_INTENT_FLAG
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val description = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            channel.description = description

            notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }

        notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentIntent(notificationPendingIntent)

        return notificationBuilder
    }

    fun updateNotificationContent(content: String) {
        notificationBuilder?.setContentText(content)
        notificationManager?.notify(NOTIFICATION_ID, notificationBuilder?.build())
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL"
        const val NOTIFICATION_REQUEST_CODE = 123
        const val NOTIFICATION_ID = 456
        val PENDING_INTENT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
    }
}