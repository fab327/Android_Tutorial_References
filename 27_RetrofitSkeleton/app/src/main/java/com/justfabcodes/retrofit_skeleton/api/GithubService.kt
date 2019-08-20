package com.justfabcodes.retrofit_skeleton.api

import com.justfabcodes.retrofit_skeleton.models.CommitData
import retrofit2.Call
import retrofit2.http.GET

interface GithubService {

    // Query the github api for commits containing the word update from the rails/rails repo
    @GET("/search/commits?q=repo:rails/rails+update")
    fun retrieveCommits(): Call<CommitData>

}