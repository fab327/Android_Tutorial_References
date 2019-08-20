package com.justfabcodes.retrofit_skeleton.util

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtil {

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}