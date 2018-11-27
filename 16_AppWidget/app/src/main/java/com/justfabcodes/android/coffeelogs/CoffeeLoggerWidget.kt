package com.justfabcodes.android.coffeelogs

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [CoffeeLoggerWidgetConfigureActivity]
 */
class CoffeeLoggerWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
//        for (appWidgetId in appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId)
//        }

        // We can run the update via a service as well
        val intent = Intent(context.applicationContext, CoffeeQuotesService::class.java)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
        context.startService(intent)
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            CoffeeLoggerPersistence(context).deletePref(appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

            val coffeeLoggerPersistence = CoffeeLoggerPersistence(context)
            val widgetText = coffeeLoggerPersistence.loadTitlePref().toString()
            val limit = coffeeLoggerPersistence.getLimitPref(appWidgetId)
            val background = if (limit <= widgetText.toInt()) R.drawable.background_overlimit else R.drawable.background

            // Construct the RemoteViews object & update UI
            val views = RemoteViews(context.packageName, R.layout.coffee_logger_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)
            views.setTextViewText(R.id.coffee_quote, getRandomQuote(context))
            views.setInt(R.id.widget_layout, "setBackgroundResource", background)

            // Register the click listeners
            views.setOnClickPendingIntent(R.id.ristretto_button, getPendingIntent(context, CoffeeTypes.RISTRETTO.grams))
            views.setOnClickPendingIntent(R.id.espresso_button, getPendingIntent(context, CoffeeTypes.ESPRESSO.grams))
            views.setOnClickPendingIntent(R.id.long_button, getPendingIntent(context, CoffeeTypes.LONG.grams))

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        private fun getPendingIntent(context: Context, value: Int): PendingIntent {
            val intent = Intent(context, MainActivity::class.java)
            intent.action = Constants.ADD_COFFEE_INTENT
            intent.putExtra(Constants.GRAMS_EXTRA, value)

            return PendingIntent.getActivity(context, value, intent, 0)
        }

        private fun getRandomQuote(context: Context): String {
            val quotes = context.resources.getStringArray(R.array.coffee_texts)
            val rand = Math.random() * quotes.size
            return quotes[rand.toInt()].toString()
        }
    }
}

