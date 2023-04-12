package com.example.tawktask.ui.main

interface MainView {

    fun changeNetworkState(state: State)
    sealed class State {
        object Connected : State()
        object NoNetwork : State()
    }
}