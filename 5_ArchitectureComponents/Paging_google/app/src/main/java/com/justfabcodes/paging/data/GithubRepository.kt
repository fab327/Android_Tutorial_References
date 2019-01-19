/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.justfabcodes.paging.data

import android.arch.paging.LivePagedListBuilder
import android.util.Log
import com.justfabcodes.paging.api.GithubService
import com.justfabcodes.paging.db.GithubLocalCache
import com.justfabcodes.paging.model.RepoSearchResult

/**
 * Repository class that works with local and remote data sources.
 */
class GithubRepository(private val service: GithubService, private val cache: GithubLocalCache) {

    /**
     * Search repositories whose names match the query.
     */
//    fun search(query: String): RepoSearchResult {
//        Log.d("GithubRepository", "New query: $query")
//        lastRequestedPage = 1
//        requestAndSaveData(query)
//
//        // Get data from the local cache
//        val data = cache.reposByName(query)
//
//        return RepoSearchResult(data, networkErrors)
//    }

    fun search(query: String): RepoSearchResult {
        Log.d("GithubRepository", "New query: $query")

        // Get data source factory from the local cache
        val dataSourceFactory = cache.reposByName(query)

        // Counstruct the boundary callback
        val boundaryCallback = RepoBoundaryCallback(query, service, cache)
        val networkErrors = boundaryCallback.networkErrors

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        // Get the network errors exposed by the boudary callback
        return RepoSearchResult(data, networkErrors)
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
        const val DATABASE_PAGE_SIZE = 20
    }
}