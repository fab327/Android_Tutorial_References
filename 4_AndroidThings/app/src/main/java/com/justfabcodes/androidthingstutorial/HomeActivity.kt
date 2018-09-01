package com.justfabcodes.androidthingstutorial

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*


/*
 * Entry point activity
 */
class HomeActivity : Activity() {

    companion object {
        val MY_TAG = "HomeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        button_input_driver.setOnClickListener {
            startActivity(Intent(this, InputDriverActivity::class.java))
        }

        button_rainbow_driver.setOnClickListener {
            startActivity(Intent(this, RainbowHatDriverActivity::class.java))
        }

        button_input_components.setOnClickListener {
            startActivity(Intent(this, _InputsActivity::class.java))
        }

        button_display_components.setOnClickListener {
            startActivity(Intent(this, _DisplayActivity::class.java))
        }

        button_other_components.setOnClickListener {
            startActivity(Intent(this, _OthersActivity::class.java))
        }
    }

}

