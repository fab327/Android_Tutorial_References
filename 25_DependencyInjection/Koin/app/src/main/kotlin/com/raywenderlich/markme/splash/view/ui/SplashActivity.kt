package com.raywenderlich.markme.splash.view.ui

import android.support.v7.app.AppCompatActivity
import com.raywenderlich.markme.main.view.ui.MainActivity
import com.raywenderlich.markme.splash.SplashContract
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SplashActivity : AppCompatActivity(), SplashContract.View {

    private val splashPresenter : SplashContract.Presenter by inject { parametersOf(this) }

    override fun onResume() {
        super.onResume()

        startActivity<MainActivity>()
        splashPresenter.onViewCreated()
    }

    override fun finishView() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        splashPresenter.onViewDestroyed()
    }
}
