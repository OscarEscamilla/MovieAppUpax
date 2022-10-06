package com.upax.moviesapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.upax.moviesapp.utils.AppConstants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel =  NotificationChannel(
                AppConstants.NOTIFICATION_CHANEL,
                "Location",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService((Context.NOTIFICATION_SERVICE)) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}