package com.example.echat

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EChatApp : Application() {
    override fun onCreate() {
        super.onCreate()

        OneSignal.initWithContext(
            context = this,
            appId = ONESIGNAL_APP_ID
        )

    }

    companion object {
        private const val ONESIGNAL_APP_ID = "d3cd033c-7513-4299-83fa-cafcb311f8b1"
    }
}
