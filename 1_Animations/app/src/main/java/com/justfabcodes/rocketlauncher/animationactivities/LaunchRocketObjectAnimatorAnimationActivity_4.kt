package com.justfabcodes.rocketlauncher.animationactivities

import android.animation.ObjectAnimator

class LaunchRocketObjectAnimatorAnimationActivity_4 : BaseAnimationActivity() {
    override fun onStartAnimation() {
        val objectAnimator = ObjectAnimator.ofFloat(rocket, "translationY", 0f, -screenHeight)

        objectAnimator.duration = DEFAULT_ANIMATION_DURATION
        objectAnimator.start()
    }
}
