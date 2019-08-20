package com.justfabcodes.retrofit_skeleton.network

import com.justfabcodes.retrofit_skeleton.api.Service
import com.justfabcodes.retrofit_skeleton.models.CommitData
import retrofit2.Callback

class GetRepositoryCommand {

    fun getRepositories(callback: Callback<CommitData>) {
        Service.githubService.retrieveCommits().enqueue(callback)
    }

}