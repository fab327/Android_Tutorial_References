package com.raywenderlich.android.motionlayout.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.raywenderlich.android.motionlayout.R
import com.raywenderlich.android.motionlayout.page.OnboardingPage
import kotlinx.android.synthetic.main.item_onboarding_page.view.*

/**
 * Custom view to map data to UI element
 */
class OnboardingPageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var page: OnboardingPage

    init {
        LayoutInflater.from(context).inflate(R.layout.item_onboarding_page, this, true)
    }

    fun setPageData(onboardingPage: OnboardingPage) {
        this.page = onboardingPage

        platformLogo.setImageResource(onboardingPage.logoResource)
        pageTitle.text = resources.getString(page.titleResource)
        pageDescription.text = resources.getString(page.descriptionResource)
    }
}