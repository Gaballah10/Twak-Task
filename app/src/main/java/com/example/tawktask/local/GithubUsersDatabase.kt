package com.example.tawktask.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tawktask.network.model.GithubUserData
import com.example.tawktask.network.model.GithubUser

@Database(entities = [GithubUser::class , GithubUserData::class], version = 1, exportSchema = false)
abstract class GithubUsersDatabase: RoomDatabase() {
    abstract fun githubNotedUserDao(): GithubNotedUserDao
    abstract fun githubCachedUserDao(): GithubAllUsersDao
}