package com.raywenderlich.android.droidwiki.ui.search

import com.raywenderlich.android.droidwiki.model.Entry

interface EntryView {
    fun displayLoading()

    fun dismissLoading()

    fun displayEntries(results: List<Entry>)

    fun displayError(error: String?)
}