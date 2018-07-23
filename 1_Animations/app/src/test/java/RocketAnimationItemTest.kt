package com.justfabcodes.rocketlauncher

import android.content.Intent
import org.junit.Assert.assertEquals
import org.junit.Test

class RocketAnimationItemTest {

    @Test
    fun getTitle() {
        val rocket = RocketAnimationItem("Fab's rocket", Intent())

        assertEquals(rocket.title, "Fab's rocket")
    }

}