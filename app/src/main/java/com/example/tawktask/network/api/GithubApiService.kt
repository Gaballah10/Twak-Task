package com.example.tawktask.network.api

import com.example.tawktask.network.model.GithubUser
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("users")
    fun callBackGithubUsersData(
        @Query("since") since: Int
    ): Single<List<GithubUser>>

    @GET("users/{username}")
    suspend fun getUserData(
        @Path("username") username: String,
    ): GithubUser
}