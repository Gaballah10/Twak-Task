package com.example.tawktask.ui.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tawktask.local.repository.DbRepository
import com.example.tawktask.network.model.GithubUser
import com.example.tawktask.network.util.DataOrException
import com.example.tawktask.useCases.NetworkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsVM @Inject constructor(
    private val networkUseCase: NetworkUseCase,
    private val repository: DbRepository
) : ViewModel() {

    suspend fun getUserData(userName: String): DataOrException<GithubUser, Boolean, Exception> {
        return networkUseCase.getUserData(userName)
    }

    fun insertUser(githubUser: GithubUser) =
        viewModelScope.launch { repository.insertNotedUser(githubUser) }

}