package com.example.tawktask.ui.main

sealed class GithubUsersAction {

    object Loading : GithubUsersAction()
    object FooterLoading : GithubUsersAction()
    object Result : GithubUsersAction()
    object DoNothing : GithubUsersAction()
    class Error(val exception: Throwable) : GithubUsersAction()

}