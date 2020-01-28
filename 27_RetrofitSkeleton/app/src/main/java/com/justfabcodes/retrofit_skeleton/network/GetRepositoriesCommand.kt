package com.justfabcodes.retrofit_skeleton.network

import com.justfabcodes.retrofit_skeleton.api.Service
import com.justfabcodes.retrofit_skeleton.models.RepoData

const val searchRepositoryQParams = "language:kotlin"
const val searchRepositorySortParams = "stars"
const val searchRepositoryOrderParams = "desc"

class GetRepositoriesCommand {

    suspend fun getRepositories(query: String, sort: String, order: String): RepoData {
        return Service.githubService.searchRepositories(query, sort, order)
    }

}