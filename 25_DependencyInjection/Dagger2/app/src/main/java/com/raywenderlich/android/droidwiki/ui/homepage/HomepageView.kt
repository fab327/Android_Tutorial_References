package com.raywenderlich.android.droidwiki.ui.homepage

import com.raywenderlich.android.droidwiki.model.WikiHomepage

interface HomepageView {
    fun displayLoading()

    fun dismissLoading()

    fun displayHomepage(result: WikiHomepage)

    fun displayError(error: String?)
}