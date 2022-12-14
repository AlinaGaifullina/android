package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.IBinder
import androidx.annotation.StringRes
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.myapplication.AppServiceActionsEnum.*

class AppService : Service() {

    private var notificationHandler: NotificationHandler? = null

    private var imageBitmap: Bitmap? = null

    private var imageUrl: String? = null
    private var condition: String? = null

    private val glide by lazy { Glide.with(baseContext) }

    override fun onCreate() {
        super.onCreate()
        notificationHandler = NotificationHandler(baseContext)
    }

    override fun onBind(intent: Intent?): IBinder? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        imageUrl = intent?.getStringExtra(IMAGE_URL_KEY)
        when (intent?.action) {
            START_SERVICE.name -> startService()
            DOWNLOAD_IMAGE.name -> downloadImage()
            SHOW_IMAGE.name -> getImage()
            STOP_SERVICE.name -> stopService()
        }
        return START_NOT_STICKY
    }

    private fun startService() {
        val notification = notificationHandler?.getNotificationBuilder(
            service = this,
            title = packageName,
            content = getString(R.string.content)
        )?.build()
        startForeground(NotificationHandler.NOTIFICATION_ID, notification)
    }

    private fun downloadImage() {
        glide.asBitmap()
            .load(imageUrl)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    condition = getString(R.string.download_fail)
                    notificationHandler?.updateNotificationContent("$condition, url: $imageUrl")
                    LocalBroadcastManager.getInstance(baseContext).sendBroadcast(createIntent(R.string.download_fail))
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    condition = getString(R.string.download_success)
                    notificationHandler?.updateNotificationContent("$condition, url: $imageUrl")
                    LocalBroadcastManager.getInstance(baseContext).sendBroadcast(createIntent(R.string.download_success))
                    return false
                }

            })
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    imageBitmap = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun getImage() {
        val imageIntent = if (imageBitmap != null) {
            createIntent(null).apply {
                putExtra(IMAGE_KEY, imageBitmap)
            }
        } else {
            createIntent(R.string.download_error)
        }
        LocalBroadcastManager.getInstance(baseContext).sendBroadcast(imageIntent)
    }

    private fun stopService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            stopForeground(true)
        }
        LocalBroadcastManager.getInstance(baseContext).sendBroadcast(createIntent(R.string.cancel_service_success))
        stopSelf()
    }

    private fun createIntent(@StringRes messageId: Int?) = Intent(IMAGE_INTENT_KEY).apply {
        putExtra(MESSAGE_KEY, messageId?.let { getString(it) })
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationHandler = null
    }

    companion object {
        const val IMAGE_INTENT_KEY = "IMAGE_INTENT_KEY"
        const val IMAGE_KEY = "IMAGE_KEY"
        const val IMAGE_URL_KEY = "IMAGE_URL_KEY"
        const val MESSAGE_KEY = "MESSAGE_KEY"
    }

}