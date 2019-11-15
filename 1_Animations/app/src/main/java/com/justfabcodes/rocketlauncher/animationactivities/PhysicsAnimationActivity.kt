package com.justfabcodes.rocketlauncher.animationactivities

import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY
import androidx.dynamicanimation.animation.SpringForce.STIFFNESS_VERY_LOW


class PhysicsAnimationActivity : BaseAnimationActivity() {
    override fun onStartAnimation() {

        SpringAnimation(rocket, DynamicAnimation.TRANSLATION_Y, -screenHeight / 2).apply {
            spring.dampingRatio = DAMPING_RATIO_HIGH_BOUNCY
            spring.stiffness = STIFFNESS_VERY_LOW
        }.also { it.start() }

    }
}
