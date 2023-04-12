package com.example.tawktask.useCases.impl

import android.util.Log
import com.example.tawktask.network.api.GithubApiService
import com.example.tawktask.network.model.GithubUser
import com.example.tawktask.network.util.DataOrException
import com.example.tawktask.useCases.NetworkUseCase
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class NetworkUseCaseImpl@Inject constructor(private val githubApiService: GithubApiService) : NetworkUseCase {

    override fun callBackGithubUsersData(since: Int): Single<List<GithubUser>> {
        return githubApiService.callBackGithubUsersData(since)
    }

    override suspend fun getUserData(username: String): DataOrException<GithubUser, Boolean, Exception> {

        val response = try {
            githubApiService.getUserData(username)

        }catch (e: Exception){
            Log.d("REX", "getGithubUser: $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE", "getGithubUser: $response")
        return  DataOrException(data = response)

    }
}