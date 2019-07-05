package com.raywenderlich.android.droidwiki.ui.search

import com.raywenderlich.android.droidwiki.model.SearchResult
import com.raywenderlich.android.droidwiki.network.Wiki
import com.raywenderlich.android.droidwiki.network.WikiApi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class EntryPresenterImpl : EntryPresenter {

    private lateinit var entryView: EntryView

    private val client: OkHttpClient = OkHttpClient()
    private val api: WikiApi = WikiApi(client)
    private val wiki: Wiki = Wiki(api)

    override fun setView(entryView: EntryView) {
        this.entryView = entryView
    }

    override fun getEntry(query: String) {
        entryView.displayLoading()
        wiki.search(query).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                entryView.dismissLoading()
                //Everything is ok, show the result if not null
                if (response?.isSuccessful == true) {
                    SearchResult(response).list?.let {
                        entryView.displayEntries(it)
                    }
                } else {
                    entryView.displayError(response?.message())
                }
            }

            override fun onFailure(call: Call?, t: IOException?) {
                entryView.displayError(t?.message)
                t?.printStackTrace()
            }
        })

    }
}