package com.raywenderlich.android.droidwiki.ui.splashscreen

import android.app.Activity
import android.os.Bundle
import com.raywenderlich.android.droidwiki.ui.homepage.HomepageActivity
import com.raywenderlich.android.droidwiki.utils.start

class Splashscreen : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HomepageActivity::class.start(this, true)
    }
}