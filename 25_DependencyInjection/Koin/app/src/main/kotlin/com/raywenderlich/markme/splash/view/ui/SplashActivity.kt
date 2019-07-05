package com.raywenderlich.markme.splash.view.ui

import android.support.v7.app.AppCompatActivity
import com.raywenderlich.markme.main.view.ui.MainActivity
import com.raywenderlich.markme.splash.SplashContract
import com.raywenderlich.markme.splash.presenter.SplashPresenter
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity(), SplashContract.View {

    private val splashPresenter : SplashContract.Presenter by lazy { SplashPresenter(this) }

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
