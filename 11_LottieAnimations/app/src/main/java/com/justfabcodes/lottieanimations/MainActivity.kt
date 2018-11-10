package com.justfabcodes.lottieanimations

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieDrawable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AsyncTask.execute {
            animation_view_programatic.setAnimation("maps.json")
            animation_view_programatic.playAnimation()
            animation_view_programatic.repeatCount = LottieDrawable.INFINITE
        }
    }

}
