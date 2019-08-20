package com.justfabcodes.retrofit_skeleton.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {

    private const val BASE_URL = "https://api.github.com"

    var githubService: GithubService
        private set

    init {
        // Handle any okHttp setup here.
        // In our case the github API requires a special header to query
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .header("Accept", "application/vnd.github.cloak-preview")
                .build()

            chain.proceed(request)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        githubService = retrofit.create(GithubService::class.java)
    }

}