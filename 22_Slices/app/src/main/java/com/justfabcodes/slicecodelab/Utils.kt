package com.justfabcodes.slicecodelab

import android.content.Context
import android.net.Uri
import androidx.core.math.MathUtils

var sReqCode = 0
var sTemperature = 16 // Celsius

fun getTemperatureString(context: Context): String {
    return context.getString(R.string.temp_string, sTemperature)
}

fun updateTemperature(context: Context, newValue: Int) {
    val newValue = MathUtils.clamp(newValue, 10, 30) // Lets keep temperatures reasonable
    if (newValue != sTemperature) {
        sTemperature = newValue

        // Should notify the URI to let any slices that might be displaying know to update.
        val uri = Uri.parse("content://com.justfabcodes.slicecodelab/temperature")
        context.contentResolver.notifyChange(uri, null)
    }
}