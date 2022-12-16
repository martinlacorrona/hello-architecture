package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import com.martinlacorrona.helloarchitecture.domain.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.domain.usecase.FetchUserListUseCase
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchUserListUseCaseImplTest {

    @RelaxedMockK
    private lateinit var userListRepository: UserListRepository

    lateinit var fetchUserListUseCase: FetchUserListUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        fetchUserListUseCase = FetchUserListUseCaseImpl(userListRepository)
    }

    @Test
    fun `fetch correctly`() = runBlocking {
        //Given
        coEvery { userListRepository.fetchUserList() } returns Resource.Success()

        //When
        val result = fetchUserListUseCase.invoke()

        //Then
        assert(result is Resource.Success)
    }

    @Test
    fun `fetch fail`() = runBlocking {
        //Given
        coEvery { userListRepository.fetchUserList() } returns Resource.Error("")

        //When
        val result = fetchUserListUseCase.invoke()

        //Then
        assert(result is Resource.Error)
    }
}