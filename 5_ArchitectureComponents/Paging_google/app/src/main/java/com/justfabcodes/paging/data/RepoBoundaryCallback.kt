package com.justfabcodes.paging.data

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import com.justfabcodes.paging.api.GithubService
import com.justfabcodes.paging.api.searchRepos
import com.justfabcodes.paging.db.GithubLocalCache
import com.justfabcodes.paging.model.Repo

/**
 * @since 12/7/18.
 */
class RepoBoundaryCallback(
        private val query: String,
        private val service: GithubService,
        private val cache: GithubLocalCache
) : PagedList.BoundaryCallback<Repo>() {

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    // LiveData of network errors.
    val networkErrors = MutableLiveData<String>()

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        requestAndSaveData(query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Repo) {
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchRepos(
                service,
                query,
                lastRequestedPage,
                GithubRepository.NETWORK_PAGE_SIZE,
                { repos ->
                    cache.insert(repos, {
                        lastRequestedPage++
                        isRequestInProgress = false
                    })
                },
                { error ->
                    networkErrors.postValue(error)
                    isRequestInProgress = false
                })
    }

}