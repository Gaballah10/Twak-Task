package com.example.tawktask.ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.tawktask.components.*
import com.example.tawktask.network.model.GithubUser
import com.example.tawktask.network.util.DataOrException
import com.example.tawktask.theme.White
import com.example.tawktask.ui.details.viewmodel.DetailsVM
import com.example.tawktask.ui.main.viewmodel.MainVM
import com.example.tawktask.useCases.NetworkConnectionUseCase

@Composable
fun DetailsScreen(
    navController: NavHostController,
    userName: String?,
    detailsVM: DetailsVM = hiltViewModel(),
    mainVM: MainVM = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val networkConnectionState by mainVM.networkStateLiveData.observeAsState()
    val localNotedUsersList = mainVM.notedUsersList.collectAsState().value
    var userNote = rememberSaveable { mutableStateOf("") }
    var isNetworkAvailable by rememberSaveable { mutableStateOf(true) }
    var userGithubUser = rememberSaveable { mutableStateOf<GithubUser>(GithubUser()) }


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
     * If network is connected get the user profile data .
     * Handle the indication for network connectivity .
     */

    when (networkConnectionState) {
        //--- If network is Connected ...
        NetworkConnectionUseCase.NetworkStates.Connected -> {

            if (!userName.isNullOrEmpty()) {
                val githubUserData = produceState<DataOrException<GithubUser, Boolean, Exception>>(
                    initialValue = DataOrException(loading = true)
                ) {
                    value = detailsVM.getUserData(userName)
                }.value

                if (githubUserData.loading == true) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BackAppBar(userName, Modifier) {
                                navController.popBackStack()
                            }
                            CircularProgressIndicator(Modifier.padding(top = 120.dp))
                        }
                    }
                } else if (githubUserData.data != null) {
                    for (i in localNotedUsersList.indices) {
                        if (localNotedUsersList[i].login == userName) {
                            userNote.value = localNotedUsersList[i].note!!
                        }
                    }
                    isNetworkAvailable = true
                    userGithubUser.value = githubUserData.data!!
                    DetailsUi(
                        navController,
                        data = userGithubUser.value,
                        detailsVM,
                        userNote,
                        isNetworkAvailable
                    )
                }
            }
        }
        NetworkConnectionUseCase.NetworkStates.NoNetwork -> {
            if (userGithubUser.value?.login != null) {
                DetailsUi(
                    navController,
                    data = userGithubUser.value,
                    detailsVM,
                    userNote,
                    isNetworkAvailable
                )
                isNetworkAvailable = false
            }
        }
    }
}

/**
 * Handle Details Screen UI....
 */

@Composable
fun DetailsUi(
    navController: NavHostController,
    data: GithubUser,
    detailsVM: DetailsVM,
    userNote: MutableState<String>,
    internetConnection: Boolean
) {
    val rememberScrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            Modifier.verticalScroll(rememberScrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BackAppBar(data.login, Modifier) {
                navController.popBackStack()
            }

            AnimatedVisibility(visible = !internetConnection) {
                InternetNoConnectionComponent()
            }

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (userImageCard, followerBar, userUtilities) = createRefs()

                Image(
                    painter = rememberImagePainter(data.avatar_url.toString()),
                    contentDescription = "User Image",
                    Modifier
                        .constrainAs(userImageCard) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                        .padding(10.dp)
                        .clip(RoundedCornerShape(0.dp))
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )

                Box(modifier = Modifier.constrainAs(followerBar) {
                    top.linkTo(userImageCard.bottom)
                }) {
                    RowTextFollowingBar(data = data)
                }

                Card(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(10.dp)
                        .constrainAs(userUtilities) {
                            top.linkTo(followerBar.bottom)
                        }, elevation = 5.dp,
                    backgroundColor = if (isSystemInDarkTheme()) White
                    else White
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        DetailsCardContent(data = data)
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            ) {
                MedTextComponents("Note")
            }
            SaveNoteComponent(userNote, data) { githubUser ->
                detailsVM.insertUser(githubUser)
            }
        }
    }
}