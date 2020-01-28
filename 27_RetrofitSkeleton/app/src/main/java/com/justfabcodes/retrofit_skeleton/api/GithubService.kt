package com.justfabcodes.retrofit_skeleton.api

import com.justfabcodes.retrofit_skeleton.models.RepoData
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    // Query the github api for commits containing the word update from the rails/rails repo
    @GET("/search/commits")
    suspend fun retrieveCommits(
        @Query("q") query: String
//          @Header("header") header: String
//          @Body("body") body: String
    ): RepoData

    @GET("/search/repositories")
    suspend fun searchRepositories(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("order") order: String
    ): RepoData

}