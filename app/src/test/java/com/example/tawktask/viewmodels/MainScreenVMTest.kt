package com.example.tawktask.viewmodels

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.runner.AndroidJUnit4
import com.example.tawktask.AppTestModule
import com.example.tawktask.local.repository.DbRepository
import com.example.tawktask.testUtils.TestUtils
import com.example.tawktask.ui.main.viewmodel.MainVM
import com.example.tawktask.useCases.NetworkConnectionUseCase
import com.example.tawktask.useCases.RemoteCallUseCases
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@HiltAndroidTest
@UninstallModules(AppTestModule::class)
@RunWith(AndroidJUnit4::class)
@Config(application = HiltTestApplication::class)
class MainScreenVMTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    lateinit var instance: MainVM
    val remoteCallUseCases: RemoteCallUseCases = mockk(relaxed = true)
    val networkConnectionUseCase: NetworkConnectionUseCase = mockk(relaxed = true)
    val mockLifecycleOwner: LifecycleOwner = mockk(relaxed = true)
    val repository: DbRepository = mockk(relaxed = true)
    val mockContext: Context = mockk(relaxed = true)

    @Before
    fun init() {
        hiltAndroidRule.inject()
        instance = MainVM(remoteCallUseCases, networkConnectionUseCase, repository)
    }

    @After
    fun unRegister() {
        unmockkAll()
    }

    @Test
    fun Refresh() {
        instance.refresh()
        verify { remoteCallUseCases.refresh() }
    }

    @Test
    fun UpdateNetworkStateWithParameter() {
        val mockState = mockk<NetworkConnectionUseCase.NetworkStates>(relaxed = true)
        val mockLiveData =
            mockk<MutableLiveData<NetworkConnectionUseCase.NetworkStates>>(relaxed = true)
        val mockObserver =
            mockk<Observer<NetworkConnectionUseCase.NetworkStates>>(relaxed = true)

        TestUtils.setProperty(instance, "networkStateLiveData", mockLiveData)

        mockLiveData.observeForever(mockObserver)
        instance.updateNetworkState(mockState)
        verify { mockLiveData.value = mockState }

        mockLiveData.removeObserver(mockObserver)
    }

    @Test
    fun ObserveNetworkConnectionWithParameter() {
        val mockObserver = mockk<Observer<NetworkConnectionUseCase.NetworkStates>>(relaxed = true)
        instance.observeNetworkConnection(mockLifecycleOwner, mockObserver)
        verify { networkConnectionUseCase.observe(mockLifecycleOwner, mockObserver) }
    }


    @Test
    fun UnRegisterNetworkConnectionCallbackWithParameter() {
        instance.unregisterNetworkConnectionCallback(mockContext)
        verify { networkConnectionUseCase.onDestroy(mockContext) }
    }
}