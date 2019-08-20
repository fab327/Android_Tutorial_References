package com.justfabcodes.retrofit_skeleton.api

import com.justfabcodes.retrofit_skeleton.models.RepoData
import retrofit2.http.GET

interface GithubService {

    // Query the github api for commits containing the word update from the rails/rails repo
    @GET("/search/commits?q=repo:rails/rails+update")
    suspend fun retrieveCommits(): RepoData

    @GET("/search/repositories?q=language:kotlin&sort=stars&order=desc")
    suspend fun searchRepositories(): RepoData

}