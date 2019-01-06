package com.justfabcodes.slicecodelab

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import androidx.core.graphics.drawable.IconCompat
import androidx.slice.Slice
import androidx.slice.SliceProvider
import androidx.slice.builders.ListBuilder
import androidx.slice.builders.SliceAction

class MySliceProvider : SliceProvider() {

    /**
     * Instantiate any required objects. Return true if the provider was successfully created,
     * false otherwise.
     */
    override fun onCreateSliceProvider(): Boolean {
        return true
    }

    /**
     * Construct the Slice and bind data if available.
     */
    override fun onBindSlice(sliceUri: Uri): Slice? {
        return when (sliceUri.path) {
            "/temperature" -> createTemperatureSlice(sliceUri)
            else -> null
        }
    }

    private fun createTemperatureSlice(sliceUri: Uri): Slice? {
        val tempUp = SliceAction(getChangeTempIntent(sTemperature + 1),
                IconCompat.createWithResource(context, R.drawable.ic_temp_up),
                "Increase temperature")

        val tempDown = SliceAction(getChangeTempIntent(sTemperature - 1),
                IconCompat.createWithResource(context, R.drawable.ic_temp_down),
                "Decrease temperature")

        // The primary action; this will activate when the row is tapped
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val openTempActivity = SliceAction(pendingIntent,
                IconCompat.createWithResource(context, R.drawable.ic_launcher_foreground), "Temperature controls")

        val listBuilder = ListBuilder(context, sliceUri, ListBuilder.INFINITY)

        val temperatureRow = ListBuilder.RowBuilder(listBuilder)
        temperatureRow.setTitle(getTemperatureString(context))

        temperatureRow.addEndItem(tempDown)
        temperatureRow.addEndItem(tempUp)
        temperatureRow.setPrimaryAction(openTempActivity)

        listBuilder.addRow(temperatureRow)

        return listBuilder.build()
    }

    private fun getChangeTempIntent(value: Int): PendingIntent {
        val intent = Intent(context, MyBroadcastReceiver::class.java)
        intent.action = MyBroadcastReceiver.ACTION_CHANGE_TEMP
        intent.putExtra(MyBroadcastReceiver.EXTRA_TEMP_VALUE, value)
        return PendingIntent.getBroadcast(context, sReqCode++, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

}
