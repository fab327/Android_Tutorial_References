package com.justfabcodes.retrofit_skeleton.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justfabcodes.retrofit_skeleton.models.CommitData
import com.justfabcodes.retrofit_skeleton.network.GetRepositoryCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    val commits = MutableLiveData<CommitData>()
    private val backgroundScope = CoroutineScope(Dispatchers.Default)

    fun updateCommitList() {
        backgroundScope.launch {
            commits.postValue(GetRepositoryCommand().getRepositories())
        }
    }

}