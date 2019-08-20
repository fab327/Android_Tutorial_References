package com.justfabcodes.retrofit_skeleton.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justfabcodes.retrofit_skeleton.models.RepoData
import com.justfabcodes.retrofit_skeleton.network.GetCommitsCommand
import com.justfabcodes.retrofit_skeleton.network.GetRepositoriesCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    var commits: MutableLiveData<RepoData>
    var repos : MutableLiveData<RepoData>
    private val backgroundScope = CoroutineScope(Dispatchers.Default)

    init {
        commits = MutableLiveData()
        repos = MutableLiveData()
    }

    fun updateCommitList() {
        backgroundScope.launch {
            commits.postValue(GetCommitsCommand().getCommits())
        }
    }

    fun updateReposList() {
        backgroundScope.launch {
            repos.postValue(GetRepositoriesCommand().getRepositories())
        }
    }

}