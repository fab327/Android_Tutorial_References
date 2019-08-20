package com.justfabcodes.retrofit_skeleton.network

import com.justfabcodes.retrofit_skeleton.api.Service
import com.justfabcodes.retrofit_skeleton.models.CommitData

class GetRepositoryCommand {

    suspend fun getRepositories(): CommitData {
        return Service.githubService.retrieveCommits()
    }

}