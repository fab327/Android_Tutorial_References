package com.justfabcodes.retrofit_skeleton.util

import android.view.View
import androidx.core.view.isVisible

fun View.toggleVisibility() {
    visibility = if (isVisible) View.GONE else View.VISIBLE
}