package com.raywenderlich.android.droidwiki.network

class Homepage(private val api: WikiApi) {
    fun get() = api.getHomepage()
}