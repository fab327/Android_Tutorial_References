package com.google.samples.motionlayoutcodelab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_step8.*

class Step8Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step8)

        coordinateMotion()
    }

    /*
     * Manually updating the progress of the motion layout when it is not the parent container
     */
    private fun coordinateMotion() {
//        val appBarListener = AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
//            val seekPosition = -verticalOffset / appbar_layout.totalScrollRange.toFloat()
//            motion_layout.progress = seekPosition
//        }

        appbar_layout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                appBarLayout?.let {
                    val seekPosition = -verticalOffset / it.totalScrollRange.toFloat()
                    motion_layout.progress = seekPosition
                }
            }
        })
    }
}
