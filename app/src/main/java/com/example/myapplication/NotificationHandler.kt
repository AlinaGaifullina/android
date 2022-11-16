package com.example.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationHandler(private val context: Context) {

    private var notificationManager: NotificationManager? = null


    fun createNotification(notificationHeader: String?, notificationBody: String?, notificationDetails: String?, intent: PendingIntent){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = context.getString(R.string.channel_name)
            val description = context.getString(R.string.channel_description)
            val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = description

            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager?.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_emoji_emotions_24)
            .setContentTitle(notificationHeader)
            .setContentText(notificationBody)
            .setContentIntent(intent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(notificationDetails))
            .setCategory(Notification.CATEGORY_MESSAGE)
            .build()

        notificationManager?.notify(111, notificationBuilder)
    }

    companion object {
        private const val CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
    }

}