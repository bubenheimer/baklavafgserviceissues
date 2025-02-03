package org.bubenheimer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.util.Log
import androidx.core.content.getSystemService
import org.bubenheimer.R.string.channelid

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val channelIdStr = getString(channelid)

        val notificationManager = getSystemService<NotificationManager>()!!

        if (notificationManager.getNotificationChannel(channelIdStr) == null) {
            Log.v("App", "Creating notification channel")

            val channel = NotificationChannel(
                channelIdStr,
                "My channel",
                IMPORTANCE_LOW
            ).apply {
                description = "My channel"
                enableLights(false)
                enableVibration(false)
                setShowBadge(true)
            }

            notificationManager.createNotificationChannel(channel)
        }
    }
}
