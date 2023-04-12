package com.example.tawktask.local.repository

import com.example.tawktask.local.GithubAllUsersDao
import com.example.tawktask.local.GithubNotedUserDao
import com.example.tawktask.network.model.GithubUserData
import com.example.tawktask.network.model.GithubUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DbRepository @Inject constructor(
    private val githubNotedUserDao: GithubNotedUserDao,
    private val githubAllUsersDao: GithubAllUsersDao
) {

    //--- For Noted User Table ( getAllNotedUsers - insertNotedUser )
    fun getAllNotedUsers(): Flow<List<GithubUser>> = githubNotedUserDao.getAllNotedUsers()
    suspend fun insertNotedUser(githubUser: GithubUser) =
        githubNotedUserDao.insertNotedUser(githubUser)

    //--- For Cached All User Table ( getAllCachedUsers - insertUser )
    fun getAllCachedUsers(): Flow<List<GithubUserData>>  = githubAllUsersDao.getAllCachedUsers()
    suspend fun insertUser(githubUser: GithubUserData) = githubAllUsersDao.insertUser(githubUser)
}