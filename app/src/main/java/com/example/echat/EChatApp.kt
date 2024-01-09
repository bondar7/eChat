package com.example.echat

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class EChatApp : Application() {
    override fun onCreate() {
        super.onCreate()

        OneSignal.initWithContext(
            context = this,
            appId = ONESIGNAL_APP_ID
        )

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(true)
        }

    }

    companion object {
        private const val ONESIGNAL_APP_ID = "505c32d7-007c-41e3-a5db-ca3edc58db05"
    }
}
