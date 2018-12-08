Tutorial on how to setup a widget

Original tutorial -> https://www.raywenderlich.com/33-android-app-widgets-tutorial

Relevant topics:
- Widget brain -> [CoffeeLoggerWidget](/16_AppWidget/app/src/main/java/com/justfabcodes/android/coffeelogs/CoffeeLoggerWidget.kt)
- Widget layout -> [coffee_logger_widget](/16_AppWidget/app/src/main/res/layout/coffee_logger_widget.xml)
- Widget configuration activity -> [CoffeeLoggerWidgetConfigureActivity](/16_AppWidget/app/src/main/java/com/justfabcodes/android/coffeelogs/CoffeeLoggerWidgetConfigureActivity.kt)
- Widget configuration layout -> [coffee_logger_widget_configure](/16_AppWidget/app/src/main/res/layout/coffee_logger_widget_configure.xml)
- Service taking care of the update -> [CoffeeQuotesService](/16_AppWidget/app/src/main/java/com/justfabcodes/android/coffeelogs/CoffeeQuotesService.kt)
- Handling retrieval of date when activity started from the widget -> [MainActivity#onCreate](/16_AppWidget/app/src/main/java/com/justfabcodes/android/coffeelogs/MainActivity.kt)
- Snackbar Undo -> [MyUndoListener](/16_AppWidget/app/src/main/java/com/justfabcodes/android/coffeelogs/MainActivity.kt)
