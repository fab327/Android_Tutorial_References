package com.raywenderlich.android.motionlayout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.android.motionlayout.page.OnboardingPage
import com.raywenderlich.android.motionlayout.view.OnboardingPageView
import com.raywenderlich.android.motionlayout.view.OnboardingView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

    private val adapter = OnboardingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = OnboardingPage
                .values()
                .map { onboardingPageData ->
                    val pageView = OnboardingPageView(this)
                    pageView.setPageData(onboardingPageData)

                    pageView
                }

        adapter.setData(data)
        onboardingView.setAdapter(adapter)
        onboardingView.listener = object : OnboardingView.OnBoardingViewListener {
            override fun notifyFinish() {
                startActivity(Intent(baseContext, TransitionActivity::class.java))
            }
        }
    }
}
