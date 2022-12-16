package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.domain.usecase.GetUserListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class GetUserListUseCaseImplTest {

    @RelaxedMockK
    private lateinit var userListRepository: UserListRepository

    private lateinit var getUserListUseCase: GetUserListUseCase

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getUserListUseCase = GetUserListUseCaseImpl(userListRepository)
    }

    //TODO: FIX THIS TESTS

    /*@Test
    fun `get 2 user list`() = runBlocking {
        //Given
        coEvery { userListRepository.getUserList("") } returns flowOf(
            arrayListOf(
                UserModel(),
                UserModel(),
            )
        )

        //When
        val result = getUserListUseCase.invoke("")

        //Then
        assertEquals(2, result.value!!.size)
    }

    @Test
    fun `get 1 user list`() = runBlocking {
        //Given
        coEvery { userListRepository.getUserList("") } returns flowOf(
            arrayListOf(
                UserModel(),
            )
        )

        //When
        val result = getUserListUseCase.invoke("")

        //Then
        assertEquals(1, result.value!!.size)
    }

    @Test
    fun `get empty user list`() = runBlocking {
        //Given
        coEvery { userListRepository.getUserList("") } returns flowOf(arrayListOf())

        //When
        val result = getUserListUseCase.invoke("")

        //Then
        assert(result.value!!.isEmpty())
    }*/
}