package com.example.tawktask.di

import com.example.tawktask.ui.main.MainView
import com.example.tawktask.ui.main.MainViewImpl
import com.example.tawktask.useCases.NetworkConnectionUseCase
import com.example.tawktask.useCases.NetworkUseCase
import com.example.tawktask.useCases.impl.NetworkConnectionUseCaseImpl
import com.example.tawktask.useCases.impl.NetworkUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Singleton
    @Binds
    abstract fun bindsNetworkUseCase(
        networkUseCaseImpl: NetworkUseCaseImpl
    ): NetworkUseCase

    @Singleton
    @Binds
    abstract fun bindsNetworkConnectionUseCase(
        networkConnectionUseCaseImpl: NetworkConnectionUseCaseImpl
    ): NetworkConnectionUseCase

    @Singleton
    @Binds
    abstract fun bindsMainView(
        MainViewImpl: MainViewImpl
    ): MainView

}
