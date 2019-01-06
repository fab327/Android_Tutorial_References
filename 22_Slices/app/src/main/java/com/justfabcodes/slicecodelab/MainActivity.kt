package com.justfabcodes.slicecodelab

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * https://codelabs.developers.google.com/codelabs/android-slices-basic/index.html?index=../../index#0
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        increase_temp.setOnClickListener(this)
        decrease_temp.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        temperature_title.text = getTemperatureString(this)
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.increase_temp -> updateTemperature(this, sTemperature + 1)
            R.id.decrease_temp -> updateTemperature(this, sTemperature - 1)
        }
        temperature_title.text = getTemperatureString(this)
    }

}
