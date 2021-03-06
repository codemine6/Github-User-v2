package com.app.githubuser.api

import com.app.githubuser.data.Repository
import com.app.githubuser.data.SearchResult
import com.app.githubuser.data.UserDetail
import com.app.githubuser.data.UserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val token = "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"

interface ApiService {
    @Headers("Authorization: token $token")
    @GET("search/users")
    fun searchUser(
        @Query("q") query: String
    ): Call<SearchResult>

    @Headers("Authorization: token $token")
    @GET("users/{login}")
    fun getUser(
        @Path("login") login: String
    ): Call<UserDetail>

    @Headers("Authorization: token $token")
    @GET("users/{login}/following")
    fun getUserFollowing(
        @Path("login") login: String
    ): Call<List<UserItem>>

    @Headers("Authorization: token $token")
    @GET("users/{login}/followers")
    fun getUserFollowers(
        @Path("login") login: String
    ): Call<List<UserItem>>

    @Headers("Authorization: token $token")
    @GET("users/{login}/repos")
    fun getUserRepositories(
        @Path("login") login: String
    ): Call<List<Repository>>
}