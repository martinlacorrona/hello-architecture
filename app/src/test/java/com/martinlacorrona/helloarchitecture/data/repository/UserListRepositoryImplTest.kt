package com.martinlacorrona.helloarchitecture.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.martinlacorrona.helloarchitecture.data.local.UserDao
import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.data.remote.UserListRemote
import com.martinlacorrona.helloarchitecture.domain.repository.UserListRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserListRepositoryImplTest {

    @RelaxedMockK
    private lateinit var userDao: UserDao

    @RelaxedMockK
    private lateinit var userListRemote: UserListRemote

    private lateinit var userListRepository: UserListRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = Dispatchers.Unconfined

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        userListRepository = UserListRepositoryImpl(userDao, userListRemote, Dispatchers.Main)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch 2 users from remote`() = runBlocking {
        //TODO
    }

    @Test
    fun `fetch 0 users from remote`() = runBlocking {
        //TODO
    }

    @Test
    fun `fetch remote not successful`() = runBlocking {
        //TODO
    }

    @Test
    fun `fetch remote empty body`() = runBlocking {
        //TODO
    }

    @Test
    fun `fetch remote unknown exception`() = runBlocking {
        //TODO
    }

    @Test
    fun `fetch remote message exception`() = runBlocking {
        //TODO
    }

    @Test
    fun `get user list`() = runBlocking {
        //Given
        val name = "testName"
        val birthday = 10L
        val listOfUsers = flowOf(
            listOf(
                UserEntity(id = 1, remoteId = 2, name = name, birthday = birthday),
                UserEntity(name = name, birthday = birthday),
                UserEntity(name = name, birthday = birthday),
            )
        )

        coEvery { userDao.getAllByName("$name%") } returns listOfUsers

        //When
        val result = userListRepository.getUserList(name).first()

        //Then
        assertEquals(3, result.size)
        assertEquals(name, result[0].name)
        assertEquals(birthday, result[0].birthday)
    }
}