package com.justfabcodes.androidthingstutorial

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.google.android.things.contrib.driver.apa102.Apa102
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.ssd1306.Ssd1306
import com.google.android.things.contrib.driver.tm1637.NumericDisplay
import java.io.IOException

private val TAG = _DisplayActivity::class.java.simpleName
private val I2C_BUS = "BUS NAME"
private val GPIO_FOR_DATA = "BUS NAME"
private val GPIO_FOR_CLOCK = "BUS NAME"
// LED configuration.
private val NUM_LEDS = 7
private val LED_BRIGHTNESS = 5 // 0 ... 31
private val LED_MODE = Apa102.Mode.BGR
private val SPI_BUS = "BUS NAME"

/**
 * Default android studio template
 */
class _DisplayActivity : Activity() {
    private lateinit var mSegmentDisplay: AlphanumericDisplay
    private lateinit var mScreen: Ssd1306
    private lateinit var mNumericSegmentDisplay: NumericDisplay
    private lateinit var mLedstrip: Apa102
    private lateinit var mLedColors: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAlphanumericDisplay()
        setupNumericDisplay()
        setupOledDisplay()
        setupLedStrip()
        val hsv = floatArrayOf(1f, 1f, 1f)
        for (i in mLedColors.indices) { // Assigns gradient colors.
            hsv[0] = i * 360f / mLedColors.size
            mLedColors[i] = Color.HSVToColor(0, hsv)
        }
        try {
            mLedstrip.write(mLedColors)
        } catch (e: IOException) {
            Log.e(TAG, "Error setting LED colors", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyAlphanumericDisplay()
        destroyNumericDisplay()
        destroyOledDisplay()
        destroyLedStrip()
    }

    private fun setupAlphanumericDisplay() {
        try {
            mSegmentDisplay = AlphanumericDisplay(I2C_BUS)
            mSegmentDisplay.setBrightness(1.0f)
            mSegmentDisplay.setEnabled(true)
            mSegmentDisplay.clear()
            mSegmentDisplay.display("ABCD")
        } catch (e: IOException) {
            Log.e(TAG, "Error configuring display", e)
        }

    }

    private fun destroyAlphanumericDisplay() {
        Log.i(TAG, "Closing display")
        try {
            mSegmentDisplay.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error closing display", e)
        }
    }

    private fun setupNumericDisplay() {
        Log.i(TAG, "Starting SegmentDisplayActivity")
        try {
            mNumericSegmentDisplay = NumericDisplay(GPIO_FOR_DATA, GPIO_FOR_CLOCK)
            mNumericSegmentDisplay.setBrightness(1.0f)
            mNumericSegmentDisplay.setColonEnabled(true)
            mNumericSegmentDisplay.display("2342")
        } catch (e: IOException) {
            Log.e(TAG, "Error configuring display", e)
        }

    }

    private fun destroyNumericDisplay() {
        Log.i(TAG, "Closing display")
        try {
            mNumericSegmentDisplay.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error closing display", e)
        }
    }

    private fun setupOledDisplay() {
        try {
            mScreen = Ssd1306(I2C_BUS)
        } catch (e: IOException) {
            Log.e(TAG, "Error while opening screen", e)
        }

        Log.d(TAG, "OLED screen activity created")
    }

    private fun destroyOledDisplay() {
        try {
            mScreen.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error closing SSD1306", e)
        }
    }

    private fun setupLedStrip() {
        mLedColors = IntArray(NUM_LEDS)
        try {
            Log.d(TAG, "Initializing LED strip")
            mLedstrip = Apa102(SPI_BUS, LED_MODE)
            mLedstrip.setBrightness(LED_BRIGHTNESS)
        } catch (e: IOException) {
            Log.e(TAG, "Error initializing LED strip", e)
        }

    }

    private fun destroyLedStrip() {
        try {
            mLedstrip.close()
        } catch (e: IOException) {
            Log.e(TAG, "Exception closing LED strip", e)
        }
    }

}
