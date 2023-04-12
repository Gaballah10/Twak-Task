package com.example.tawktask.network.util

sealed class NetworkAction {
    object Connected : NetworkAction()
    object NotConnected : NetworkAction()

}