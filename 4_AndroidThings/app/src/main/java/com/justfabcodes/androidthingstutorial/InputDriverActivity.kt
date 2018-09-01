package com.justfabcodes.androidthingstutorial

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.button.ButtonInputDriver
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManager
import com.justfabcodes.androidthingstutorial.HomeActivity.Companion.MY_TAG
import java.io.IOException

/*
 * Generic button driver activity
 */
private var buttonInputDriver: ButtonInputDriver? = null
private var ledGpio: Gpio? = null

class InputDriverActivity : Activity() {

    private val BUTTON_PIN_NAME: String = BoardDefaults.gpioForButton
    private val LED_PIN_NAME: String = BoardDefaults.gpioForLED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val peripheralManager: PeripheralManager = PeripheralManager.getInstance()
        Log.d(MY_TAG, "Available GPIO: " + peripheralManager.getGpioList())

        try {
            buttonInputDriver = ButtonInputDriver(BUTTON_PIN_NAME,
                    Button.LogicState.PRESSED_WHEN_LOW,
                    KeyEvent.KEYCODE_SPACE)
            buttonInputDriver?.register()

            ledGpio = peripheralManager.openGpio(LED_PIN_NAME)
            ledGpio?.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        } catch (e: IOException) {
            Log.w(MY_TAG, "Error opening GPIO", e)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_SPACE) {
            setLedValue(true)
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_SPACE) {
            setLedValue(false)
            return true
        }

        return super.onKeyUp(keyCode, event)
    }

    override fun onDestroy() {
        buttonInputDriver?.let {
            it.unregister()
            try {
                it.close()
            } catch (e: IOException) {
                Log.e(MY_TAG, "Error closing Button Driver", e)
            }
        }
        ledGpio?.let {
            try {
                it.close()
            } catch (e: IOException) {
                Log.e(MY_TAG, "Error closing GPIO", e)
            }
        }

        super.onDestroy()
    }

    private fun setLedValue(value: Boolean) {
        try {
            ledGpio?.value = value
        } catch (e: IOException) {
            Log.e(MY_TAG, "Error updating GPIO value", e)
        }
    }

}
