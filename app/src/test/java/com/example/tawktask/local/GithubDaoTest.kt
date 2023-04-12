package com.example.tawktask.local

import androidx.test.runner.AndroidJUnit4
import com.example.tawktask.AppTestModule
import com.example.tawktask.local.repository.DbRepository
import com.example.tawktask.network.model.GithubUser
import com.example.tawktask.network.model.GithubUserData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@HiltAndroidTest
@UninstallModules(AppTestModule::class)
@RunWith(AndroidJUnit4::class)
@Config(application = HiltTestApplication::class)
class GithubDaoTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    val githubNotedUserDao = mockk<GithubNotedUserDao>()
    val githubAllUsersDao = mockk<GithubAllUsersDao>()
    lateinit var instance: DbRepository

    @Before
    fun init() {
        hiltAndroidRule.inject()
        instance = DbRepository(githubNotedUserDao,githubAllUsersDao)
    }

    @Test
    fun testGetAllNotedUsers() = runBlockingTest {
        // Given
        val expectedUsers = listOf(
            GithubUser("user 1", 1),
            GithubUser("user 2", 2),
            GithubUser("user 3", 3)
        )
       // val githubNotedUserDao = mockk<GithubNotedUserDao>()
        coEvery { githubNotedUserDao.getAllNotedUsers() } returns flowOf(expectedUsers)

        // When
        val users = githubNotedUserDao.getAllNotedUsers()
        val actualUsers = users.toList().flatten()

        // Then
        assertEquals(expectedUsers, actualUsers)
    }

    @Test
    fun testInsertNotedUser() = runBlockingTest {
        // Given
        val githubUser = GithubUser("user 1", 1)

        // Mock the DAO's insertNotedUser function
        coEvery { githubNotedUserDao.insertNotedUser(githubUser) } returns Unit

        // When
        instance.insertNotedUser(githubUser)

        // Then
        coVerify { githubNotedUserDao.insertNotedUser(githubUser) }
    }

    @Test
    fun getAllCachedUsersTest()  = runBlockingTest {
        val expectedUsers = listOf(
            GithubUserData("user 1", 1),
            GithubUserData("user 2", 2),
            GithubUserData("user 3", 3)
        )
        coEvery { githubAllUsersDao.getAllCachedUsers() } returns flowOf(expectedUsers)

        // When
        val users = githubAllUsersDao.getAllCachedUsers()
        val actualUsers = users.toList().flatten()

        // Then
        assertEquals(expectedUsers, actualUsers)
    }

    @Test
    fun insertUserGithubUserDataTest() = runBlockingTest{
        // Given
        var githubUser = GithubUserData()
        githubUser.login = "User 1"
        githubUser.id = 1

        coEvery { githubAllUsersDao.insertUser(githubUser) } returns Unit
        instance.insertUser(githubUser)
        coVerify { githubAllUsersDao.insertUser(githubUser) }
    }
}