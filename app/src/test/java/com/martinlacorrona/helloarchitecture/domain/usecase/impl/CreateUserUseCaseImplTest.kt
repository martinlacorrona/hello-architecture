package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.repository.UserRepository
import com.martinlacorrona.helloarchitecture.domain.usecase.CreateUserUseCase
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CreateUserUseCaseImplTest {

    @RelaxedMockK
    private lateinit var userRepository: UserRepository

    lateinit var createUserUseCase: CreateUserUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        createUserUseCase = CreateUserUseCaseImpl(userRepository)
    }

    @Test
    fun `create user correctly`() = runBlocking {
        //Given
        val userModel = UserModel()
        coEvery { userRepository.createUser(userModel) } returns Resource.Success()

        //When
        val result = createUserUseCase.invoke(userModel)

        //Then
        assert(result is Resource.Success)
    }

    @Test
    fun `create user error`() = runBlocking {
        //Given
        val userModel = UserModel()
        coEvery { userRepository.createUser(userModel) } returns Resource.Error("")

        //When
        val result = createUserUseCase.invoke(userModel)

        //Then
        assert(result is Resource.Error)
    }
}