package com.example.tawktask.navigation

import java.lang.IllegalArgumentException

enum class TawkScreens {

    MainScreen,
    DetailsScreen;

    companion object {
         fun fromRoute(route: String?): TawkScreens
          = when(route?.substringBefore("/")) {
             MainScreen.name -> MainScreen
             DetailsScreen.name -> DetailsScreen
             null -> MainScreen
             else -> throw IllegalArgumentException("Route $route is not recognized")
          }
    }
}