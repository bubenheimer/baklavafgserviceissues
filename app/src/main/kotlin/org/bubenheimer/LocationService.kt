package org.bubenheimer

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Notification.CATEGORY_SERVICE
import android.app.Service
import android.content.Intent
import android.location.LocationManager
import android.os.IBinder
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE
import androidx.core.content.getSystemService
import org.bubenheimer.R.drawable.ic_stat_name
import org.bubenheimer.R.id.notification_id_service
import org.bubenheimer.R.string.channelid
import kotlin.concurrent.thread

class LocationService : Service() {
    @SuppressLint("MissingPermission")
    override fun onCreate() {
        Log.d("LocationService", "onCreate()")

        super.onCreate()

        val notification: Notification =
                NotificationCompat.Builder(this, getString(channelid))
                        .setContentTitle("Foreground service in progress")
                        .setCategory(CATEGORY_SERVICE)
                        .setOngoing(true)
                        .setSmallIcon(ic_stat_name)
                        .setForegroundServiceBehavior(FOREGROUND_SERVICE_IMMEDIATE)
                        .build()

        startForeground(notification_id_service, notification)

        val locationManager = getSystemService<LocationManager>()!!
        val allProviders: MutableList<String> = locationManager.allProviders
        val provider: String = allProviders.first { locationManager.isProviderEnabled(it) }

        locationManager.requestLocationUpdates(
            provider,
            5_000L,
            0f
        ) { println("onLocationChanged: $it") }

        thread {
            while (true) {
                println("Service process still alive")

                SystemClock.sleep(5_000L)
            }
        }
    }

    override fun onDestroy() {
        Log.d("LocationService", "onDestroy()")

        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
