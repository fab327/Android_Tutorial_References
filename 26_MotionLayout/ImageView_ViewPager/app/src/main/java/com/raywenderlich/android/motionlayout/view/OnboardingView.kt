package com.raywenderlich.android.motionlayout.view

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import com.raywenderlich.android.motionlayout.R
import com.raywenderlich.android.motionlayout.page.OnboardingPage
import kotlinx.android.synthetic.main.onboarding_view.view.*

class OnboardingView : FrameLayout, ViewPager.OnPageChangeListener {

    interface OnBoardingViewListener {
        fun notifyFinish()
    }

    var listener: OnBoardingViewListener? = null
    private val numberOfPages by lazy { OnboardingPage.values().size }

    constructor(context: Context?) : super(context) {
        initializeUi(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initializeUi(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initializeUi(context)
    }

    private fun initializeUi(context: Context?) {
        LayoutInflater.from(context).inflate(R.layout.onboarding_view, this, true)

        setupListeners()
    }

    private fun setupListeners() {
        pagesList.addOnPageChangeListener(this)
        pageIndicator.setViewPager(pagesList)

        previousButton.setOnClickListener { pagesList.setCurrentItem(pagesList.currentItem - 1, true) }
        nextButton.setOnClickListener { pagesList.setCurrentItem(pagesList.currentItem + 1, true) }
        finishButton.setOnClickListener {
            Toast.makeText(context, R.string.onboarding_finished, Toast.LENGTH_SHORT).show()
            listener?.let {
                it.notifyFinish()
            }
        }
    }

    fun setAdapter(adapter: PagerAdapter) {
        pagesList.adapter = adapter
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (numberOfPages > 1) {
            val newProgress = (position + positionOffset) / (numberOfPages - 1)

            onboardingRoot.progress = newProgress
        }
    }

    override fun onPageScrollStateChanged(state: Int) = Unit
    override fun onPageSelected(position: Int) = Unit
}