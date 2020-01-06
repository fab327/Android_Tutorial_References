package com.justfabcodes.rocketlauncher.animationactivities

class LaunchAndSpinViewPropertyAnimatorAnimationActivity_7 : BaseAnimationActivity() {
    override fun onStartAnimation() {
        rocket.animate()
            .translationY(-screenHeight)
            .rotationBy(360f)
            .setDuration(DEFAULT_ANIMATION_DURATION)
            .start()
    }
}
