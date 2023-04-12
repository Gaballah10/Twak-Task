package com.example.tawktask.network.util

class DataOrException<T, Boolean, E: Exception>(
    var data: T? = null,
    var loading: kotlin.Boolean? = null,
    var e: E? = null)