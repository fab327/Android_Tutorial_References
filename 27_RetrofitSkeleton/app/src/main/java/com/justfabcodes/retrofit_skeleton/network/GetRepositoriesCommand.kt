package com.justfabcodes.retrofit_skeleton.network

import com.justfabcodes.retrofit_skeleton.api.Service
import com.justfabcodes.retrofit_skeleton.models.RepoData

class GetRepositoriesCommand {

    suspend fun getRepositories(): RepoData {
        return Service.githubService.searchRepositories()
    }

}