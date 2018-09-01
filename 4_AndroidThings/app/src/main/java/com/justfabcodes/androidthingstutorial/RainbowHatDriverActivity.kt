package com.justfabcodes.androidthingstutorial

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import com.google.android.things.contrib.driver.bmx280.Bmx280
import com.google.android.things.contrib.driver.ht16k33.Ht16k33
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat


/**
 * Activity to learn how to use the rainbow hat driver
 * https://androidthings.withgoogle.com/#!/drivers/rainbowhat
 */
class RainbowHatDriverActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        toggleRedLED()
//        displaySegmentOnDisplay("fab")
//        playBuzzer()
//        logTemperature()
//        lightUpTheRainbow()
//        buzzWhenButtonBisPressed()

        monitorTemperature()
        registerButtonForEventListening()
    }

    private fun toggleRedLED() {
        // Light up the Red LED.
        val led = RainbowHat.openLedRed()

        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                led.value = !led.value
            }

            override fun onFinish() {
                // Close the device when done.
                led.close()
            }
        }.start()
    }

    private fun displaySegmentOnDisplay(value: String) {
        // Display a string on the segment display.
        val segment = RainbowHat.openDisplay()
        segment.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX)
        segment.display(value)
        segment.setEnabled(true)
        // Close the device when done.
        segment.close()
    }

    private fun playBuzzer() {
        // Play a note on the buzzer.
        val buzzer = RainbowHat.openPiezo()
        buzzer.play(440.0)
        Handler().postDelayed({
            buzzer.stop()
            // Close the device when done.
            buzzer.close()
        }, 4000) // Stop the buzzer after 4 seconds.
    }

    private fun logTemperature() {
        // Log the current temperature
        val sensor = RainbowHat.openSensor()
        sensor.temperatureOversampling = Bmx280.OVERSAMPLING_1X
        displaySegmentOnDisplay(sensor.readTemperature().toString())
        // Close the device when done.
        sensor.close()
    }

    private fun lightUpTheRainbow() {
        // Light up the rainbow
        val ledStrip = RainbowHat.openLedStrip()
        ledStrip.brightness = 31
        val rainbow = IntArray(RainbowHat.LEDSTRIP_LENGTH)
        for (i in rainbow.indices) {
            rainbow[i] = Color.HSVToColor(255, floatArrayOf(i * 360f / rainbow.size, 1.0f, 1.0f))
        }
        ledStrip.write(rainbow)
        // Close the device when done.
        ledStrip.close()
    }

    private fun buzzWhenButtonBisPressed() {
        // Detect when button 'B' is pressed.
        val button = RainbowHat.openButtonB()
        button.setOnButtonEventListener { button, pressed ->
            if (pressed) {
                playBuzzer()
                button.close()
            }
        }
    }

    private fun monitorTemperature() {
        // Continuously report temperature.
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerDynamicSensorCallback(object : SensorManager.DynamicSensorCallback() {
            override fun onDynamicSensorConnected(sensor: Sensor) {
                if (sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                    sensorManager.registerListener(object : SensorEventListener {
                        override fun onSensorChanged(event: SensorEvent) {
                            displaySegmentOnDisplay(event.values[0].toString())
                        }
                        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                            displaySegmentOnDisplay(accuracy.toString())
                            Log.i(HomeActivity.MY_TAG, "accuracy changed: $accuracy")
                        }
                    },
                            sensor, SensorManager.SENSOR_DELAY_NORMAL)
                }
            }
        })
    }

    private fun registerButtonForEventListening() {
        val inputDriver = RainbowHat.createButtonCInputDriver(KeyEvent.KEYCODE_A)
        inputDriver.register()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_C) {
            //Handle event here
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_C) {
            //Handle event here
        }
        return super.onKeyUp(keyCode, event)
    }

}