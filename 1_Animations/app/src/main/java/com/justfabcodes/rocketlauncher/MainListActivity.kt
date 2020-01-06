package com.justfabcodes.rocketlauncher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.justfabcodes.rocketlauncher.animationactivities.*
import java.util.*


class MainListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        val items = ArrayList<RocketAnimationItem>()

//        items.add(
//                RocketAnimationItem(
//                        getString(R.string.title_no_animation),
//                        Intent(this, NoAnimationActivity::class.java)
//                )
//        )

        items.add(
            RocketAnimationItem(
                getString(R.string.title_launch_rocket),
                Intent(this, LaunchRocketValueAnimatorAnimationActivity_1::class.java)
            )
        )

        items.add(
            RocketAnimationItem(
                getString(R.string.title_spin_rocket),
                Intent(this, RotateRocketAnimationActivity_2::class.java)
            )
        )

        items.add(
            RocketAnimationItem(
                getString(R.string.title_accelerate_rocket),
                Intent(this, AccelerateRocketAnimationActivity_3::class.java)
            )
        )

        items.add(
            RocketAnimationItem(
                getString(R.string.title_launch_rocket_objectanimator),
                Intent(this, LaunchRocketObjectAnimatorAnimationActivity_4::class.java)
            )
        )

        items.add(
            RocketAnimationItem(
                getString(R.string.title_color_animation),
                Intent(this, ColorAnimationActivity_5::class.java)
            )
        )

        items.add(
            RocketAnimationItem(
                getString(R.string.launch_spin),
                Intent(this, LaunchAndSpinAnimatorSetAnimationActivity::class.java)
            )
        )

        items.add(
            RocketAnimationItem(
                getString(R.string.launch_spin_viewpropertyanimator),
                Intent(this, LaunchAndSpinViewPropertyAnimatorAnimationActivity_7::class.java)
            )
        )

        items.add(
            RocketAnimationItem(
                getString(R.string.title_with_doge),
                Intent(this, FlyWithDogeAnimationActivity_8::class.java)
            )
        )

        items.add(
            RocketAnimationItem(
                getString(R.string.title_animation_events),
                Intent(this, WithListenerAnimationActivity_9::class.java)
            )
        )

        items.add(
            RocketAnimationItem(
                getString(R.string.title_there_and_back),
                Intent(this, FlyThereAndBackAnimationActivity_10::class.java)
            )
        )

        items.add(
            RocketAnimationItem(
                getString(R.string.title_jump_and_blink),
                Intent(this, XmlAnimationActivity_11::class.java)
            )
        )

        items.add(
            RocketAnimationItem(
                getString(R.string.title_physics_animation),
                Intent(this, PhysicsAnimationActivity_12::class.java)
            )
        )

        recyclerView.adapter = RocketAdapter(this, items)
    }
}
