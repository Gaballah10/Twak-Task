package com.example.tawktask.network

import com.example.tawktask.network.api.BaseGithubApi
import com.example.tawktask.BuildConfig
import com.example.tawktask.network.api.GithubApiService

object GithubUsersNetwork {
    fun getGithubUsersApi(baseUrl: String = BuildConfig.BASE_URL): GithubApiService {
        return BaseGithubApi(
            baseUrl = baseUrl
        )
    }
}