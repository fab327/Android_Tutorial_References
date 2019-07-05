package com.raywenderlich.android.droidwiki.ui.search

interface EntryPresenter {
    fun setView(entryView: EntryView)

    fun getEntry(query: String)
}