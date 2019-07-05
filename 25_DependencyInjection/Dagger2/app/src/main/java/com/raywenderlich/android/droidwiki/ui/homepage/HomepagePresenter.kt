package com.raywenderlich.android.droidwiki.ui.homepage

interface HomepagePresenter {
    fun setView(homepageView: HomepageView)

    fun loadHomepage()
}