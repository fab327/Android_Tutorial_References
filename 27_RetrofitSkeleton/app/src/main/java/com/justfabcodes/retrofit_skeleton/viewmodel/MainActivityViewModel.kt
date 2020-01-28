package com.justfabcodes.retrofit_skeleton.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justfabcodes.retrofit_skeleton.models.RepoData
import com.justfabcodes.retrofit_skeleton.network.*
import kotlinx.coroutines.*

class MainActivityViewModel : ViewModel() {

    var commits: MutableLiveData<RepoData>
    var repos : MutableLiveData<RepoData>
    private val backgroundScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    init {
        commits = MutableLiveData()
        repos = MutableLiveData()
    }

    fun updateCommitList() {
        backgroundScope.launch {
            commits.postValue(GetCommitsCommand().getCommits(retrieveCommitsQueryParams))
        }
    }

    fun updateReposList() {
        backgroundScope.launch {
            repos.postValue(GetRepositoriesCommand()
                .getRepositories(searchRepositoryQParams, searchRepositorySortParams, searchRepositoryOrderParams))
        }
    }

    override fun onCleared() {
        backgroundScope.cancel()
        super.onCleared()
    }

    /**
     * Deprecated way of doing things, use PagedListAdapter instead
     */
    fun shouldLoadMoreData(lastVisibleItem: Int, totalItemCount: Int) {
        if ( lastVisibleItem == (totalItemCount - 1) ) {
            //Do something more like loading more data
        }
    }

}