package com.example.homework4

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button

class FlightModeReceiver(private var alertView: View) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isAirplaneModeEnabled = Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON,
                0
            ) != 0

//            coroutineBtn?.isEnabled = !isAirplaneModeEnabled
//            sendBtn.isEnabled = !isAirplaneModeEnabled
            if (!isAirplaneModeEnabled) {
                alertView.visibility = View.GONE
            } else {
                alertView.visibility = View.VISIBLE
            }
        }
    }
}
