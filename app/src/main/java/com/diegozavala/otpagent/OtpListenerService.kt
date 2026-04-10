package com.diegozavala.otpagent

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class OtpListenerService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName == packageName) return
        if (sbn.isOngoing) return

        val extras = sbn.notification.extras
        val title = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString().orEmpty()
        val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString().orEmpty()
        val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString().orEmpty()

        val combined = listOf(title, text, bigText)
            .filter { it.isNotBlank() }
            .joinToString(" ")

        if (combined.isBlank()) return

        val otp = OtpExtractor.extract(combined) ?: return

        ClipboardHelper.copyOtp(this, otp)
    }
}
