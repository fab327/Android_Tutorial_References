package com.justfabcodes.android.coffeelogs

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText

/**
 * The configuration screen for the [CoffeeLoggerWidget] AppWidget.
 */
class CoffeeLoggerWidgetConfigureActivity : Activity() {

    internal val coffeeLoggerPersistence = CoffeeLoggerPersistence(this)
    internal var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    internal lateinit var mAppWidgetText: EditText

    internal var mOnClickListener: View.OnClickListener = View.OnClickListener {
        val context = this@CoffeeLoggerWidgetConfigureActivity

        // When the button is clicked, store the string locally
        val widgetText = mAppWidgetText.text.toString()
        coffeeLoggerPersistence.saveLimitPref(widgetText.toInt(), mAppWidgetId)

        // It is the responsibility of the configuration activity to update the app widget
        val appWidgetManager = AppWidgetManager.getInstance(context)
        CoffeeLoggerWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId)

        // Make sure we pass back the original appWidgetId
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
        setResult(Activity.RESULT_OK, resultValue)
        finish()
    }

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(Activity.RESULT_CANCELED)

        setContentView(R.layout.coffee_logger_widget_configure)
        mAppWidgetText = findViewById<View>(R.id.appwidget_text) as EditText
        findViewById<View>(R.id.add_button).setOnClickListener(mOnClickListener)

        // Find the widget id from the intent.
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }
    }

}

