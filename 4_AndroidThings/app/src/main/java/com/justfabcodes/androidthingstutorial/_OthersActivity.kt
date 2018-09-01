package com.justfabcodes.androidthingstutorial

import android.app.Activity
import android.os.Bundle
import java.io.IOException
import android.util.Log
import com.google.android.things.contrib.driver.pwmspeaker.Speaker

private val TAG = _OthersActivity::class.java.simpleName
private val PWM_BUS = "BUS NAME"

/**
 * Default android studio template
 */
class _OthersActivity : Activity() {
    private lateinit var mSpeaker: Speaker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSpeaker()
        try {
            mSpeaker.play(/* G4 */391.995)
        } catch (e: IOException) {
            Log.e(TAG, "Error playing note", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroySpeaker()
    }

    private fun setupSpeaker() {
        try {
            mSpeaker = Speaker(PWM_BUS)
            mSpeaker.stop() // in case the PWM pin was enabled already
        } catch (e: IOException) {
            Log.e(TAG, "Error initializing speaker")
        }

    }

    private fun destroySpeaker() {
        try {
            mSpeaker.stop()
            mSpeaker.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error closing speaker", e)
        }
    }

}
