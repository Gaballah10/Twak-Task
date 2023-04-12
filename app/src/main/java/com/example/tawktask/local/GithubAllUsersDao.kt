package com.example.tawktask.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tawktask.network.model.GithubUserData
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubAllUsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(githubUser: GithubUserData)

    @Query("SELECT * FROM github_all_users_table")
    fun getAllCachedUsers(): Flow<List<GithubUserData>>

}