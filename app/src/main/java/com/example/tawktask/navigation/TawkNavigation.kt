package com.example.tawktask.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tawktask.ui.details.DetailsScreen
import com.example.tawktask.ui.main.MainScreen


@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun TawkNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = TawkScreens.MainScreen.name
    ) {

        composable(TawkScreens.MainScreen.name) {
            MainScreen(navController = navController)
        }

        val details = TawkScreens.DetailsScreen.name
        composable(
            "$details/{userName}",
            arguments = listOf(navArgument("userName") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName")
            DetailsScreen(
                navController = navController,
                userName
            )

        }
    }
}