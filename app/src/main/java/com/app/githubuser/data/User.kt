package com.app.githubuser.data

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("items")
    val items: List<UserItem>
)

data class UserItem(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String
)

data class UserDetail(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("blog")
    val blog: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("email")
    val email: String
)