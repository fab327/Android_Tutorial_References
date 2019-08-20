package com.justfabcodes.retrofit_skeleton.network

import com.justfabcodes.retrofit_skeleton.api.Service
import com.justfabcodes.retrofit_skeleton.models.RepoData

class GetCommitsCommand {

    suspend fun getCommits(): RepoData {
        return Service.githubService.retrieveCommits()
    }

}