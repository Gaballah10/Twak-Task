package com.example.tawktask.network.api

import com.example.tawktask.network.util.NetworkUtil
import com.example.tawktask.network.model.GithubUser
import io.reactivex.Single
import javax.inject.Inject

open class BaseGithubApi @Inject constructor(baseUrl: String) : GithubApiService {

    /**
     * Get Api Service With Using Base URL ...
     */

    private var apiService: GithubApiService = NetworkUtil.getRetrofit(
        baseUrl
    ).create(GithubApiService::class.java)

    override fun callBackGithubUsersData(since: Int): Single<List<GithubUser>> {
        return apiService.callBackGithubUsersData(since)
    }

    override suspend fun getUserData(username: String): GithubUser {
        return apiService.getUserData(username)
    }
}