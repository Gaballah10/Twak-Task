package com.example.tawktask.useCases

import com.example.tawktask.network.model.GithubUser
import com.example.tawktask.network.util.DataOrException
import io.reactivex.Single
import retrofit2.Response

interface NetworkUseCase {

    fun callBackGithubUsersData(
        since: Int
    ): Single<List<GithubUser>>

    suspend fun getUserData(
        username: String
    ): DataOrException<GithubUser, Boolean, Exception>

}