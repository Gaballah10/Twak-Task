package com.example.tawktask.di

import android.content.Context
import androidx.room.Room
import com.example.tawktask.network.GithubUsersNetwork
import com.example.tawktask.BuildConfig
import com.example.tawktask.local.GithubAllUsersDao
import com.example.tawktask.local.GithubNotedUserDao
import com.example.tawktask.local.GithubUsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNewsApiBaseUrl() = GithubUsersNetwork.getGithubUsersApi(BuildConfig.BASE_URL)

    @Singleton
    @Provides
    fun provideGithubUsersDao(githubUsersDatabase: GithubUsersDatabase): GithubNotedUserDao =
        githubUsersDatabase.githubNotedUserDao()

    @Singleton
    @Provides
    fun provideGithubCachedUsersDao(githubUsersDatabase: GithubUsersDatabase): GithubAllUsersDao =
        githubUsersDatabase.githubCachedUserDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): GithubUsersDatabase =
        Room.databaseBuilder(
            context,
            GithubUsersDatabase::class.java,
            "users.db"
        )
            .fallbackToDestructiveMigration()
            .build()
}

