package com.justfabcodes.retrofit_skeleton.models

import com.google.gson.annotations.SerializedName

data class RepoData(val items: MutableList<Item>)

data class Item(
    val id: Long?,

    //Commits
    val commit: Commit?,
    @SerializedName("sha")
    val commitHash: String?,

    //Repos
    val name: String?,
    val description: String?
)

data class Commit(
    val author: Author?,
    @SerializedName("message")
    val commitMessage: String?
)

data class Author(val name: String?)