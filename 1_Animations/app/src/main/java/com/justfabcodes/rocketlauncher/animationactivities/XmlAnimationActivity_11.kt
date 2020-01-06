package com.justfabcodes.rocketlauncher.animationactivities

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import com.justfabcodes.rocketlauncher.R
import com.microsoft.appcenter.analytics.Analytics

class XmlAnimationActivity_11 : BaseAnimationActivity() {
    override fun onStartAnimation() {
        val rocketAnimatorSet =
            AnimatorInflater.loadAnimator(this, R.animator.jump_and_blink) as AnimatorSet
        rocketAnimatorSet.setTarget(rocket)

        val dogeAnimatorSet =
            AnimatorInflater.loadAnimator(this, R.animator.jump_and_blink) as AnimatorSet
        dogeAnimatorSet.setTarget(doge)

        val bothAnimatorSet = AnimatorSet()
        bothAnimatorSet.playTogether(rocketAnimatorSet, dogeAnimatorSet)

        bothAnimatorSet.duration = DEFAULT_ANIMATION_DURATION
        bothAnimatorSet.start()

        Analytics.trackEvent("Animation started", mutableMapOf("XmlAnimation" to "Yes"))
    }
}
