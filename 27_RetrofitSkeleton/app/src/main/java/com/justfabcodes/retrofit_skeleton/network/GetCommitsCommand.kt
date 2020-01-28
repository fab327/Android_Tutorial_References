package com.justfabcodes.retrofit_skeleton.network

import com.justfabcodes.retrofit_skeleton.api.Service
import com.justfabcodes.retrofit_skeleton.models.RepoData

const val retrieveCommitsQueryParams = "repo:rails/rails+update"

class GetCommitsCommand {

    suspend fun getCommits(query: String): RepoData {
        return Service.githubService.retrieveCommits(query)
    }

}