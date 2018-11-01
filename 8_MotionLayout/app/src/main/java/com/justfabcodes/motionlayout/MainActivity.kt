package com.justfabcodes.motionlayout

import android.os.Bundle
import android.support.constraint.motion.MotionLayout
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            motion_container.transitionToEnd()
            motion_container.setTransitionListener(
                object : MotionLayout.TransitionListener {
                    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                        seekbar.progress = ceil(p3 * 100).toInt()
                    }

                    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                        if (p1 == R.id.ending_set) {
                            motion_container.transitionToStart()
                        }
                    }
                }
            )
        }
    }

}