package com.example.tawktask.ui.main

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tawktask.components.*
import com.example.tawktask.navigation.TawkScreens
import com.example.tawktask.network.model.GithubUser
import com.example.tawktask.ui.main.viewmodel.MainVM
import com.example.tawktask.useCases.NetworkConnectionUseCase

@Composable
fun MainScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    navController: NavHostController,
    mainVM: MainVM = hiltViewModel()
) {
    val localNotedUsersList = mainVM.notedUsersList.collectAsState().value
    val localCachedUsersList = mainVM.cachedUsersList.collectAsState().value
    val usersList = mainVM.usersMutableList.collectAsState().value
    val networkConnectionState by mainVM.networkStateLiveData.observeAsState()
    val liveAction by mainVM.liveDataAction.observeAsState()
    val searchValOnline = rememberSaveable { mutableStateOf("empty") }
    val searchLocalVal = rememberSaveable { mutableStateOf("empty") }
    var isNetworkAvailable by rememberSaveable { mutableStateOf(true) }
    val listState = rememberLazyListState()

    /**
     * Update and Observe
     */

    mainVM.observeNetworkConnection(
        lifecycleOwner,
        Observer {
            mainVM.updateNetworkState(it)
        }
    )
    /**
     * Handle Network State ....
     * Connected / NotConnected (Shimmer - Load Cached Data - Search In Local Data)
     */
    when (networkConnectionState) {
        //--- If network is Connected ...
        NetworkConnectionUseCase.NetworkStates.Connected -> {
            if (usersList.isEmpty()) {
                Log.d("StateOfLoadingData1",usersList.size.toString())
                searchLocalVal.value = "empty"
                searchValOnline.value = "empty"
                mainVM.refresh()
            }
            isNetworkAvailable = true
        }
        //--- If No Network (Shimmer - Load Cached Data - Search In Local Data)
        NetworkConnectionUseCase.NetworkStates.NoNetwork -> {
            isNetworkAvailable = false
            if (localCachedUsersList.isNotEmpty() && usersList.isEmpty() && searchLocalVal.value == "empty") {
                showListOfCachedUsers(
                    localCachedUsersList,
                    mainVM,
                    listState,
                    navController,
                    searchValOnline,
                    localNotedUsersList,
                    isNetworkAvailable
                ) {
                    searchLocalVal.value = it
                }
            }
            if (localCachedUsersList.isNotEmpty() && usersList.isEmpty() && searchLocalVal.value != "empty") {
                val searchedData = localCachedUsersList.filter {
                    it.login.contains(searchLocalVal.value)
                }
                showListOfCachedUsers(
                    searchedData,
                    mainVM,
                    listState,
                    navController,
                    searchValOnline,
                    localNotedUsersList,
                    isNetworkAvailable
                ) {
                    searchLocalVal.value = it
                }
            }
            if (localCachedUsersList.isEmpty() && usersList.isEmpty()) {
                Column(
                    Modifier
                        .fillMaxSize()
                ) {
                    SearchComponent { searchValue ->
                        searchValOnline.value = searchValue
                        if (searchValue == "empty") {
                            mainVM.isFooterLoading.value = false
                            mainVM.livedataLoadNextPage.value = true
                            searchValue.apply { "" }
                        }
                    }
                    InternetNoConnectionComponent()
                    repeat(7) {
                        AnimatedShimmer()
                    }
                }
            }
        }
    }

    /**
     * Update UI Compose State (Loading - Show Result - Load Footer - Search - Paging State)
     */
    when (liveAction) {
        //--- Show Loading ...
        is GithubUsersAction.Loading -> {
            ShowLoadingProgress(mainVM.isMainLoading.value!!)
        }
        //--- Show Footer Loading ...
        is GithubUsersAction.FooterLoading -> {
            showListOfUsers(
                usersList,
                mainVM,
                listState,
                navController,
                mainVM.isFooterLoading.value,
                searchValOnline,
                localNotedUsersList,
                isNetworkAvailable
            )
        }
        //--- Show Footer Loading ...
        is GithubUsersAction.Result -> {
            Log.d("StateOfLoadingData",usersList.size.toString())
            if (searchValOnline.value == "empty") {
                showListOfUsers(
                    usersList,
                    mainVM,
                    listState,
                    navController,
                    mainVM.isFooterLoading.value,
                    searchValOnline,
                    localNotedUsersList,
                    isNetworkAvailable
                )

            } else {
                //--- Filter Data ...
                val searchedData = localNotedUsersList.filter {
                    it.login.contains(searchValOnline.value) || it.note!!.contains(searchValOnline.value)
                }
                mainVM.isFooterLoading.value = false
                mainVM.livedataLoadNextPage.value = false
                showListOfUsers(
                    searchedData,
                    mainVM,
                    listState,
                    navController,
                    mainVM.isFooterLoading.value,
                    searchValOnline,
                    localNotedUsersList,
                    isNetworkAvailable
                )
            }
        }
        //---
        is GithubUsersAction.Error -> {
            Log.d("OnError", "On Error Happen ...")
        }
        is GithubUsersAction.DoNothing -> {
            showListOfUsers(
                usersList,
                mainVM,
                listState,
                navController,
                false,
                searchValOnline,
                localNotedUsersList,
                isNetworkAvailable
            )
        }
        else -> {}
    }
}

/**
 * Main UI Component Design In Compose ..
 */

@Composable
fun showListOfUsers(
    githubUserList: List<GithubUser>,
    viewModel: MainVM,
    listState: LazyListState,
    navController: NavController,
    showFooter: Boolean?,
    userName: MutableState<String>,
    localUsersList: List<GithubUser>,
    isNetworkAvailable: Boolean
) {
    val rememberScrollState = rememberScrollState()
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState)) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            //--- Search For Item Online ( Note - Username )
            SearchComponent { searchValue ->
                userName.value = searchValue
                if (searchValue == "empty") {
                    viewModel.isFooterLoading.value = false
                    viewModel.livedataLoadNextPage.value = true
                    searchValue.apply { "" }
                }
            }
            //--- UI Component For Network Connectivity Indication
            AnimatedVisibility(visible = !isNetworkAvailable) {
                InternetNoConnectionComponent()
            }
            //--- Lazy Column for retrieve the data ( inverted - not inverted item )
            LazyColumn(
                state = listState
            ) {
                items(githubUserList.size) { index ->
                    val data = githubUserList[index]
                    viewModel.isFooterLoading.value = false
                    if (index % 4 == 0 && userName.value == "empty") {
                        UserItemInverted(modifier = Modifier, localUsersList, { githubUser ->
                            val details = TawkScreens.DetailsScreen.name
                            navController.navigate("$details/${githubUser.login}")
                        }, githubUser = data)
                        if (index >= githubUserList.size - 1 &&
                            viewModel.livedataLoadNextPage.value == true
                        ) {
                            SideEffect {
                                viewModel.isFooterLoading.value = true
                                viewModel.livedataLoadNextPage.value = false
                                viewModel.loadRemoteData()
                            }
                        }
                    } else {
                        UserItem(modifier = Modifier, localUsersList, { githubUser ->
                            val details = TawkScreens.DetailsScreen.name
                            navController.navigate("$details/${githubUser.login}")
                        }, githubUser = data)
                        if (index >= githubUserList.size - 1 &&
                            viewModel.livedataLoadNextPage.value == true
                        ) {
                            SideEffect {
                                viewModel.isFooterLoading.value = true
                                viewModel.livedataLoadNextPage.value = false
                                viewModel.loadRemoteData()
                            }
                        }
                    }
                }
            }
        }
        //--- Load Paging Indicator State ...
        if (showFooter!!) {
            Box(modifier = Modifier.padding(bottom = 60.dp)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp), elevation = 0.dp
                ) {
                    ConstraintLayout {
                        val (progressFooter) = createRefs()
                        CircularProgressIndicator(
                            modifier = Modifier.constrainAs(
                                progressFooter
                            ) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            })
                    }
                }
            }
        } else {
            Box {}
        }
    }
}

/**
 * Load Footer Paging Spinner With Animation
 */

@Composable
fun ShowLoadingProgress(showMainProgress: Boolean) {
    AnimatedVisibility(visible = showMainProgress) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
}
