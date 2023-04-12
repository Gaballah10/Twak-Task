package com.example.tawktask.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.tawktask.R
import com.example.tawktask.navigation.TawkNavigation
import com.example.tawktask.theme.ModifiedDesign
import com.example.tawktask.theme.PrimaryDesignDark
import com.example.tawktask.ui.main.MainView
import com.example.tawktask.ui.main.observers.NetworkObserver
import com.example.tawktask.ui.main.viewmodel.MainVM
import com.example.tawktask.theme.TawkTaskTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainVM: MainVM by viewModels()

    @Inject
    lateinit var views: MainView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            this.window.statusBarColor = if (isSystemInDarkTheme()) {
                ContextCompat.getColor(this, R.color.PrimaryDesignDark)
            } else {
                ContextCompat.getColor(this, R.color.PrimaryDesign)
            }
            TawkTaskTheme {
                TwakNavigationApp()
            }
        }
        mainVM.registerNetworkConnectionCallback(applicationContext)
        bindObservers()
    }

    private fun bindObservers() {
        mainVM.observeNetworkState(
            this@MainActivity,
            NetworkObserver(views)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mainVM.unregisterNetworkConnectionCallback(applicationContext)
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun TwakNavigationApp() {
    Surface(color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize(), content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TawkNavigation()
            }
        })
}