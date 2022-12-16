package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import com.martinlacorrona.helloarchitecture.domain.repository.UserRepository
import com.martinlacorrona.helloarchitecture.domain.usecase.DeleteUserUseCase
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteUserUseCaseImplTest {

    @RelaxedMockK
    private lateinit var userRepository: UserRepository

    lateinit var deleteUseCase: DeleteUserUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        deleteUseCase = DeleteUserUseCaseImpl(userRepository)
    }

    @Test
    fun `delete user correctly`() = runBlocking {
        //Given
        val userId = 1
        coEvery { userRepository.removeUser(userId) } returns Resource.Success()

        //When
        val result = deleteUseCase.invoke(userId)

        //Then
        assert(result is Resource.Success)
    }

    @Test
    fun `delete user error`() = runBlocking {
        //Given
        val userId = 1
        coEvery { userRepository.removeUser(userId) } returns Resource.Error("")

        //When
        val result = deleteUseCase.invoke(userId)

        //Then
        assert(result is Resource.Error)
    }
}