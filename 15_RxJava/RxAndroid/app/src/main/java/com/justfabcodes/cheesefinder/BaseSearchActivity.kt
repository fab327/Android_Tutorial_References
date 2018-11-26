
package com.justfabcodes.cheesefinder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cheeses.*

abstract class BaseSearchActivity : AppCompatActivity() {

    protected lateinit var cheeseSearchEngine: CheeseSearchEngine
    private val cheeseAdapter = CheeseAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheeses)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = cheeseAdapter

        cheeseSearchEngine = CheeseSearchEngine(resources.getStringArray(R.array.cheeses))
    }

    protected fun showProgress() {
        progressBar.visibility = VISIBLE
    }

    protected fun hideProgress() {
        progressBar.visibility = GONE
    }

    protected fun showResult(result: List<String>) {
        if (result.isEmpty()) {
            Toast.makeText(this, R.string.nothing_found, Toast.LENGTH_SHORT).show()
        }
        cheeseAdapter.cheeses = result
    }

}