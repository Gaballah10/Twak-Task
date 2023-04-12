package com.example.tawktask.ui.main.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.tawktask.local.repository.DbRepository
import com.example.tawktask.network.model.GithubUserData
import com.example.tawktask.network.model.GithubUser
import com.example.tawktask.ui.main.GithubUsersAction
import com.example.tawktask.useCases.NetworkConnectionUseCase
import com.example.tawktask.useCases.RemoteCallUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val remoteCallUseCases: RemoteCallUseCases,
    private val networkConnectionUseCase: NetworkConnectionUseCase,
    private val repository: DbRepository
) : ViewModel() {

    var usersMutableList = MutableStateFlow(emptyList<GithubUser>())
    var isMainLoading = MutableLiveData<Boolean>()
    var isFooterLoading = MutableLiveData<Boolean>()
    val liveDataAction: MutableLiveData<GithubUsersAction> = MutableLiveData<GithubUsersAction>()
    var livedataLoadNextPage = MutableLiveData<Boolean>().apply { false }
    val networkStateLiveData: LiveData<NetworkConnectionUseCase.NetworkStates> =
        MutableLiveData<NetworkConnectionUseCase.NetworkStates>().apply {
            value = NetworkConnectionUseCase.NetworkStates.NoNetwork
        }

    private val _notedUsersList = MutableStateFlow<List<GithubUser>>(emptyList())
    val notedUsersList = _notedUsersList.asStateFlow()

    private val _cachedUsersList = MutableStateFlow<List<GithubUserData>>(emptyList())
    val cachedUsersList = _cachedUsersList.asStateFlow()

    /**
     * Get LocalData from 2 tables inside Users Database ... ( Cached Users - Noted Users )
     */


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllCachedUsers().distinctUntilChanged().collect { listOfCachedUsers ->
                if (listOfCachedUsers.isNullOrEmpty()) {
                    Log.d("listOfCached", ": Empty listOfCachedUsers ")
                } else {
                    _cachedUsersList.value = listOfCachedUsers
                    Log.d("listOfUsers", ":${cachedUsersList.value.size} ")
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotedUsers().distinctUntilChanged()
                .collect { listOfUsers ->
                    if (listOfUsers.isNullOrEmpty()) {
                        Log.d("listOfUsers", ": Empty listOfUsers ")
                    } else {
                        _notedUsersList.value = listOfUsers
                        Log.d("listOfUsers", ":${notedUsersList.value.size} ")
                    }
                }
        }
    }

    //--- Reload UI Github User List ...
    fun refresh() {
        remoteCallUseCases.refresh()
        loadRemoteData()
    }

    /**
     * Load Github User List
     */

    fun loadRemoteData() {
        remoteCallUseCases.load(object : RemoteCallUseCases.OnLoadListener {

            override fun loading() {
                liveDataAction.value = GithubUsersAction.Loading
                isMainLoading.value = true
            }

            override fun footerLoading() {
                liveDataAction.value = GithubUsersAction.FooterLoading
                isFooterLoading.value = true
            }

            override fun loaded(data: List<GithubUser>) {
                liveDataAction.value = GithubUsersAction.Result
                livedataLoadNextPage.value = true
                isMainLoading.value = false
                isFooterLoading.value = false
                usersMutableList.value = usersMutableList.value + data
                cacheAllData(data)
            }

            override fun doNothing() {
                liveDataAction.value = GithubUsersAction.DoNothing
            }

            override fun error(ex: Throwable) {
                liveDataAction.value = GithubUsersAction.Error(ex)
                Timber.e(ex)
            }
        })
    }

    /**
     * Cache UserGithub List ...
     */

    fun cacheAllData(data: List<GithubUser>) {
        viewModelScope.launch {
            for (i in data.indices) {
                var githubUserData = GithubUserParse(data[i])
                repository.insertUser(githubUserData)
            }
        }
    }

    /**
     * Network Connection States
     */
    fun updateNetworkState(state: NetworkConnectionUseCase.NetworkStates) {
        (networkStateLiveData as MutableLiveData<NetworkConnectionUseCase.NetworkStates>).value =
            state
    }

    fun registerNetworkConnectionCallback(context: Context) {
        networkConnectionUseCase.onStart(context)
    }

    fun unregisterNetworkConnectionCallback(context: Context) {
        networkConnectionUseCase.onDestroy(context)
    }

    fun observeNetworkConnection(
        lifecycleOwner: LifecycleOwner,
        observer: Observer<NetworkConnectionUseCase.NetworkStates>
    ) {
        networkConnectionUseCase.observe(
            lifecycleOwner,
            observer
        )
    }

    fun observeNetworkState(
        lifecycleOwner: LifecycleOwner,
        observer: Observer<NetworkConnectionUseCase.NetworkStates>
    ) {
        networkStateLiveData.observe(
            lifecycleOwner,
            observer
        )
    }

    fun GithubUserParse(data: GithubUser): GithubUserData {
        return GithubUserData(
            data.login,
            data.id,
            data.node_id,
            data.avatar_url,
            data.gravatarId,
            data.url,
            data.html_url,
            data.followers_url,
            data.following_url,
            data.gists_url,
            data.starred_url,
            data.subscriptions_url,
            data.organizations_url,
            data.repos_url,
            data.events_url,
            data.received_events_url,
            data.type,
            data.site_admin,
            data.name,
            data.company,
            data.blog,
            data.location,
            data.email,
            data.hireable,
            data.bio,
            data.twitter_username,
            data.public_repos,
            data.public_gists,
            data.followers,
            data.following,
            data.created_at,
            data.updated_at,
            data.note
        )
    }
}