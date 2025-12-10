package com.euphoria.selfcare.euphoria

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // Panggil NotificationHelper untuk menampilkan notifikasi
        NotificationHelper.showNotification(context)
    }
}
