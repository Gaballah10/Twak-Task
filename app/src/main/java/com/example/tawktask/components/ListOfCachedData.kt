package com.example.tawktask.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.tawktask.navigation.TawkScreens
import com.example.tawktask.network.model.GithubUser
import com.example.tawktask.network.model.GithubUserData
import com.example.tawktask.ui.main.viewmodel.MainVM

@Composable
fun showListOfCachedUsers(
    githubUserList: List<GithubUserData>,
    viewModel: MainVM,
    listState: LazyListState,
    navController: NavController,
    userName: MutableState<String>,
    localUsersList: List<GithubUser>,
    isNetworkAvailable: Boolean,
    onSearchedCachedClicked:(String) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            //--- Search For Item Depend on username ...
            SearchComponent { searchValue ->
                userName.value = searchValue
                onSearchedCachedClicked(searchValue)
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
            //--- Lazy Column for retrieve the data (inverted - not inverted item)
            LazyColumn(
                state = listState
            ) {
                items(githubUserList.size) { index ->
                    val data = githubUserList[index]
                    viewModel.isFooterLoading.value = false
                    if (index % 4 == 0 && userName.value == "empty") {
                        UserItemInvertedCached(modifier = Modifier, localUsersList, { githubUser ->
                            val details = TawkScreens.DetailsScreen.name
                            navController.navigate("$details/${githubUser.login}")
                        }, githubUserData = data)
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
                        UserItemCached(modifier = Modifier, localUsersList, { githubUser ->
                            val details = TawkScreens.DetailsScreen.name
                            navController.navigate("$details/${githubUser.login}")
                        }, githubUserData = data)
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
    }
}