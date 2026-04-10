package com.diegozavala.otpagent

import android.content.ComponentName
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnOpenSettings).setOnClickListener {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }
    }

    override fun onResume() {
        super.onResume()
        updateStatus()
    }

    private fun updateStatus() {
        val enabled = isNotificationListenerEnabled()
        val statusText = findViewById<TextView>(R.id.tvStatus)
        if (enabled) {
            statusText.text = getString(R.string.status_active)
            statusText.setTextColor(Color.parseColor("#4CAF50"))
        } else {
            statusText.text = getString(R.string.status_inactive)
            statusText.setTextColor(Color.parseColor("#F44336"))
        }
    }

    private fun isNotificationListenerEnabled(): Boolean {
        val flat = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        val componentName = ComponentName(this, OtpListenerService::class.java).flattenToString()
        return flat?.contains(componentName) == true
    }
}
