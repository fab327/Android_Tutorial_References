package com.raywenderlich.android.motionlayout.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.raywenderlich.android.motionlayout.R
import com.raywenderlich.android.motionlayout.page.OnboardingPage
import kotlinx.android.synthetic.main.onboarding_view.view.*

/**
 * Custom view with viewPager setup logic
 */
class OnboardingView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    interface OnBoardingViewListener {
        fun notifyFinish()
    }

    var listener: OnBoardingViewListener? = null
    private val numberOfPages by lazy { OnboardingPage.values().size }

    init {
        LayoutInflater.from(context).inflate(R.layout.onboarding_view, this, true)

        setupListeners()
    }

    private fun setupListeners() {
        viewPager.addOnPageChangeListener(this)
        pageIndicator.setViewPager(viewPager)

        previousButton.setOnClickListener { viewPager.setCurrentItem(viewPager.currentItem - 1, true) }
        nextButton.setOnClickListener { viewPager.setCurrentItem(viewPager.currentItem + 1, true) }
        finishButton.setOnClickListener {
            Toast.makeText(context, R.string.onboarding_finished, Toast.LENGTH_SHORT).show()
            listener?.let {
                it.notifyFinish()
            }
        }
    }

    fun setAdapter(adapter: PagerAdapter) {
        viewPager.adapter = adapter
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