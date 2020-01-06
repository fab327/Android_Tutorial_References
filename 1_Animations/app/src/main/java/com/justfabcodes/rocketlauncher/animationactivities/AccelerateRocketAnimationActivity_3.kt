package com.justfabcodes.rocketlauncher.animationactivities

import android.animation.ValueAnimator
import android.view.animation.AccelerateInterpolator

class AccelerateRocketAnimationActivity_3 : BaseAnimationActivity() {
    override fun onStartAnimation() {
        val valueAnimator = ValueAnimator.ofFloat(0f, -screenHeight)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            rocket.translationY = value
            doge.translationY = value
        }

        valueAnimator.interpolator = AccelerateInterpolator(1.5f)
        valueAnimator.duration = DEFAULT_ANIMATION_DURATION

        valueAnimator.start()
    }
}
