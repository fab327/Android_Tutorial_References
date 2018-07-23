package com.justfabcodes.rocketlauncher

import android.support.test.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test

class RocketAdapterTest {

    @Test
    fun getItemCount() {
        val rocket = RocketAdapter(InstrumentationRegistry.getTargetContext(), emptyList())

        Assert.assertEquals(rocket.itemCount, 0)
    }

}