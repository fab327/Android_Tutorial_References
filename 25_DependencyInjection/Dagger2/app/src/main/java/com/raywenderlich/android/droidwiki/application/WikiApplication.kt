package com.raywenderlich.android.droidwiki.application

import android.app.Application
import com.raywenderlich.android.droidwiki.dagger.AppComponent
import com.raywenderlich.android.droidwiki.dagger.AppModule
import com.raywenderlich.android.droidwiki.dagger.DaggerAppComponent

class WikiApplication : Application() {

    lateinit var wikiComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        wikiComponent = initDagger(this)
    }

    private fun initDagger(app: WikiApplication): AppComponent =
            DaggerAppComponent.builder()
                    .appModule(AppModule(app))
                    .build()

}