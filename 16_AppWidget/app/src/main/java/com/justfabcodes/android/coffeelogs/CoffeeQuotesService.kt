package com.justfabcodes.android.coffeelogs

import android.app.IntentService
import android.appwidget.AppWidgetManager
import android.content.Intent

class CoffeeQuotesService : IntentService("CoffeeQuotesService") {

    override fun onHandleIntent(intent: Intent?) {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val allWidgetIds = intent?.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)

        allWidgetIds?.let {
            it.forEach { appWidgetId ->
                CoffeeLoggerWidget.updateAppWidget(this, appWidgetManager, appWidgetId)
            }
        }
    }

}