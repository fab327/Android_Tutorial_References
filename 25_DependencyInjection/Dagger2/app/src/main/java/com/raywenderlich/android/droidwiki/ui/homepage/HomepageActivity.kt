package com.raywenderlich.android.droidwiki.ui.homepage

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.raywenderlich.android.droidwiki.R
import com.raywenderlich.android.droidwiki.model.WikiHomepage
import com.raywenderlich.android.droidwiki.ui.search.SearchActivity
import com.raywenderlich.android.droidwiki.utils.errorDialog
import com.raywenderlich.android.droidwiki.utils.parseHtml
import com.raywenderlich.android.droidwiki.utils.start
import kotlinx.android.synthetic.main.activity_homepage.*

class HomepageActivity : Activity(), HomepageView {

    private val presenter: HomepagePresenter = HomepagePresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        presenter.setView(this)
        presenter.loadHomepage()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.homepage, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.search -> {
                    SearchActivity::class.start(this)
                    true
                }

                else -> {
                    super.onOptionsItemSelected(item)
                }
            }

    // Implementation of HomepageView

    override fun displayLoading() {
        wait_progress_bar.post {
            wait_progress_bar.visibility = View.VISIBLE
            homepage_sv.visibility = View.GONE
        }
    }

    override fun dismissLoading() {
        wait_progress_bar.post {
            wait_progress_bar.visibility = View.GONE
            homepage_sv.visibility = View.VISIBLE
        }
    }

    override fun displayHomepage(result: WikiHomepage) {
        homepage_tv.post {
            homepage_tv.text = result.htmlContent.parseHtml()
        }
    }

    override fun displayError(error: String?) {
        Log.e("ERROR", error)
        runOnUiThread {
            R.string.error.errorDialog(this)
        }
    }
}
