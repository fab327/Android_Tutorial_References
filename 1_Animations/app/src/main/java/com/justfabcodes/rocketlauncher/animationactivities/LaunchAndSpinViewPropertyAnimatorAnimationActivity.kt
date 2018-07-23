package com.justfabcodes.rocketlauncher.animationactivities

class LaunchAndSpinViewPropertyAnimatorAnimationActivity : BaseAnimationActivity() {
    override fun onStartAnimation() {
        rocket.animate()
            .translationY(-screenHeight)
            .rotationBy(360f)
            .setDuration(BaseAnimationActivity.DEFAULT_ANIMATION_DURATION)
            .start()
    }
}
