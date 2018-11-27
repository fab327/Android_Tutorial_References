package com.raywenderlich.android.coffeelogs

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.IBinder

class CoffeeQuotesService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val allWidgetIds = intent?.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)

        allWidgetIds?.let { allWidgetIds ->
            allWidgetIds.forEach {appWidgetId ->
                CoffeeLoggerWidget.updateAppWidget(this, appWidgetManager, appWidgetId)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}
