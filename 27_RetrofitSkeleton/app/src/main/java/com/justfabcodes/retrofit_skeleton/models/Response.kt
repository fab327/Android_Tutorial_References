package com.justfabcodes.retrofit_skeleton.models

import com.google.gson.annotations.SerializedName

data class CommitData(var items: List<Item>)

data class Item(
    val id: Long?,
    val commit: Commit?,
    @SerializedName("sha")
    val commitHash: String?
)

data class Commit(
    val author: Author?,
    @SerializedName("message")
    val commitMessage: String?
)

data class Author(val name: String?)