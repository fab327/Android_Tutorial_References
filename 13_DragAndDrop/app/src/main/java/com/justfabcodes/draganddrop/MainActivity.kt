package com.justfabcodes.draganddrop

import android.content.ClipData
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

/*
 * Initial tutorial -> http://www.vogella.com/tutorials/AndroidDragAndDrop/article.html
 * Documentation -> https://developer.android.com/guide/topics/ui/drag-drop
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindUiElement()
    }

    private fun bindUiElement() {
        topLeftImage.setOnTouchListener(MyTouchListener())
        bottomLeftImage.setOnTouchListener(MyTouchListener())
        topRightImage.setOnTouchListener(MyTouchListener())
        bottomRightImage.setOnTouchListener(MyTouchListener())

        topLeftContainer.setOnDragListener(MyDragListener())
        bottomLeftContainer.setOnDragListener(MyDragListener())
        topRightContainer.setOnDragListener(MyDragListener())
        bottomRightContainer.setOnDragListener(MyDragListener())
    }

    class MyTouchListener : View.OnTouchListener {
        override fun onTouch(view: View?, motionEvent: MotionEvent): Boolean {
            return when {
                motionEvent.action == MotionEvent.ACTION_DOWN -> {
                    val data = ClipData.newPlainText("", "")
                    val shadowBuilder = View.DragShadowBuilder(view)
                    view?.startDrag(data, shadowBuilder, view, 0)
                    view?.visibility = View.INVISIBLE
                    true
                }
                else -> false
            }

        }
    }

    inner class MyDragListener : View.OnDragListener {
        val enterShape: Drawable =  resources.getDrawable(R.drawable.shape_drop_target)
        val normalShape: Drawable = resources.getDrawable(R.drawable.shape)

        override fun onDrag(view: View?, event: DragEvent): Boolean {
            val action: Int = event.action;
            when(action) {
//                DragEvent.ACTION_DRAG_STARTED -> {}
                DragEvent.ACTION_DRAG_ENTERED -> view?.background = enterShape
                DragEvent.ACTION_DRAG_EXITED -> view?.background = normalShape
                DragEvent.ACTION_DROP -> {
                    val v: View = event.localState as View
                    val owner: ViewGroup = v.parent as ViewGroup
                    owner.removeView(v)
                    val container: LinearLayout = view as LinearLayout
                    container.addView(v)
                    v.visibility = View.VISIBLE
                }
                DragEvent.ACTION_DRAG_ENDED -> view?.background = normalShape
                else -> {}
            }
            return true
        }
    }
}
