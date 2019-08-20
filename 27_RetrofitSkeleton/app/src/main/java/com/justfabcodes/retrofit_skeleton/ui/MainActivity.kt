package com.justfabcodes.retrofit_skeleton.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.justfabcodes.retrofit_skeleton.R
import com.justfabcodes.retrofit_skeleton.models.CommitData
import com.justfabcodes.retrofit_skeleton.network.GetRepositoryCommand
import com.justfabcodes.retrofit_skeleton.util.NetworkUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val getRepoCommand = GetRepositoryCommand()
    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        repositories.layoutManager = LinearLayoutManager(this)
        repositories.adapter = RepoAdapter(CommitData(emptyList()))
        repositories.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        getData.setOnClickListener {
            if (NetworkUtil.isNetworkConnected(this@MainActivity)) {
                //Coroutines recipes https://proandroiddev.com/android-coroutine-recipes-33467a4302e9
                uiScope.launch {
                    val result = withContext(Dispatchers.Default) {
                        getRepoCommand.getRepositories()
                    }
                    (repositories.adapter as RepoAdapter).updateDataSet(result)

                    /*
                     * Alternatively with async/await
                     *
                        val result2 = async {
                            getRepoCommand.getRepositories()
                        }
                        (repositories.adapter as RepoAdapter).updateDataSet(result2.await())
                     */

                }
            }
        }
    }
}