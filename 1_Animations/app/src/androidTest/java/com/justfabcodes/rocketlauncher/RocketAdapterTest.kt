package com.justfabcodes.rocketlauncher

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test

class RocketAdapterTest {

    @Test
    fun getItemCount() {
        val rocket = RocketAdapter(InstrumentationRegistry.getTargetContext(), emptyList())

        Assert.assertEquals(rocket.itemCount, 0)
    }

}