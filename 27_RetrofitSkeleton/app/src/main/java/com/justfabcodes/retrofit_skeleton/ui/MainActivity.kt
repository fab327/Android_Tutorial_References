package com.justfabcodes.retrofit_skeleton.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.justfabcodes.retrofit_skeleton.R
import com.justfabcodes.retrofit_skeleton.models.RepoData
import com.justfabcodes.retrofit_skeleton.network.GetCommitsCommand
import com.justfabcodes.retrofit_skeleton.util.NetworkUtil
import com.justfabcodes.retrofit_skeleton.util.toggleVisibility
import com.justfabcodes.retrofit_skeleton.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity(), RepoAdapter.RepoAdapterListener {

    private val getRepoCommand = GetCommitsCommand()
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        initViews()
    }

    override fun onItemClick(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun initViews() {
        repositories.layoutManager = LinearLayoutManager(this)
        repositories.adapter = RepoAdapter(RepoData(mutableListOf())).also {
            it.setListener(this)
        }
        repositories.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        (repositories.adapter as RepoAdapter).enableDragFunctionality(repositories)

        getCommits.setOnClickListener {
            if (NetworkUtil.isNetworkConnected(this@MainActivity)) {
                /*
                    Coroutines recipes https://proandroiddev.com/android-coroutine-recipes-33467a4302e9


                    Without viewModel

                    uiScope.launch {
                    val result = withContext(Dispatchers.Default) {
                        getRepoCommand.getCommits()
                    }
                    (repositories.adapter as RepoAdapter).updateDataSet(result)

                    OR

                    val result2 = async {
                        getRepoCommand.getCommits()
                    }
                    (repositories.adapter as RepoAdapter).updateDataSet(result2.await())

                }
                 */

                progressBar.toggleVisibility()
                viewModel.updateCommitList()
            }
        }

        getRepos.setOnClickListener {
            if (NetworkUtil.isNetworkConnected(this@MainActivity)) {
                progressBar.toggleVisibility()
                viewModel.updateReposList()
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        viewModel.commits.observe(this, Observer { commitData ->
            progressBar.toggleVisibility()

            (repositories.adapter as RepoAdapter).updateDataSet(commitData)
        })

        viewModel.repos.observe(this, Observer { reposData ->
            progressBar.toggleVisibility()

            (repositories.adapter as RepoAdapter).updateDataSet(reposData)
        })
    }
}