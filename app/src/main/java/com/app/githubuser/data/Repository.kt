package com.app.githubuser.data

import com.google.gson.annotations.SerializedName

data class Repository(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("stargazers_count")
    val stargazersCount: Int,

    @field:SerializedName("watchers_count")
    val watchersCount: Int,

    @field:SerializedName("language")
    val language: String,

    @field:SerializedName("forks_count")
    val forksCount: Int,

    @field:SerializedName("forks")
    val forks: Int
)