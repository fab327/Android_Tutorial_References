package com.justfabcodes.rocketlauncher

import android.app.Application
import com.facebook.soloader.SoLoader
import com.facebook.sonar.android.AndroidSonarClient
import com.facebook.sonar.android.utils.SonarUtils
import com.facebook.sonar.plugins.inspector.DescriptorMapping
import com.facebook.sonar.plugins.inspector.InspectorSonarPlugin
import com.facebook.sonar.plugins.network.NetworkSonarPlugin
import com.facebook.sonar.plugins.sharedpreferences.SharedPreferencesSonarPlugin
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import com.microsoft.appcenter.push.Push

/**
 * This additional class is required to learn about Sonar
 * https://fbsonar.com/docs/getting-started.html
 *
 * @since 7/17/18.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCenter.start(this, "248fb88d-b993-42bb-ace7-2fd75c06adc3",
            Analytics::class.java, Crashes::class.java, Push::class.java, Distribute::class.java)

        if (BuildConfig.DEBUG && SonarUtils.shouldEnableSonar(this)) {
            SoLoader.init(this, false)
            val client = AndroidSonarClient.getInstance(this)
            val descriptorMapping = DescriptorMapping.withDefaults()
            val networkSonarPlugin = NetworkSonarPlugin()
            val sharedPreferences = SharedPreferencesSonarPlugin(this, "Animation_App")

            client.addPlugin(InspectorSonarPlugin(this.applicationContext, descriptorMapping))
            client.addPlugin(networkSonarPlugin)
            client.addPlugin(sharedPreferences)

            client.start()
        }

        val preferences = getSharedPreferences("Animation_App", android.content.Context.MODE_PRIVATE)
        val launchCount = preferences.getInt(LAUNCH_COUNT_KEY, 0)
        preferences.edit()
            .putInt(LAUNCH_COUNT_KEY, launchCount + 1)
            .commit()
    }

    companion object {
        const val LAUNCH_COUNT_KEY = "LaunchCount"
    }

}