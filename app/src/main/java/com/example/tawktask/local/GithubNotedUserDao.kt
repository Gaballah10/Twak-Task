package com.example.tawktask.local


import androidx.room.*
import com.example.tawktask.network.model.GithubUser
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubNotedUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotedUser(githubUser: GithubUser)

    @Query("SELECT * FROM github_noted_users_table")
    fun getAllNotedUsers(): Flow<List<GithubUser>>

    @Delete
    suspend fun deleteNotedUserItem(githubUser: GithubUser)
}