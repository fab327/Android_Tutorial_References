package com.justfabcodes.slicecodelab

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_CHANGE_TEMP = "com.justfabcodes.slicecodelab.ACTION_CHANGE_TEMP"
        const val EXTRA_TEMP_VALUE = "com.justfabcodes.slicecodelab.EXTRA_TEMP_VALUE"
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_CHANGE_TEMP -> if (intent.extras != null) {
                val newValue = intent.extras.getInt(EXTRA_TEMP_VALUE, sTemperature)
                updateTemperature(context, newValue)
            }
        }
    }
}