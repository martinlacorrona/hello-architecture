package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.repository.UserRepository
import com.martinlacorrona.helloarchitecture.domain.usecase.EditUserUseCase
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class EditUserUseCaseImplTest {

    @RelaxedMockK
    private lateinit var userRepository: UserRepository

    lateinit var editUserUseCase: EditUserUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        editUserUseCase = EditUserUseCaseImpl(userRepository)
    }

    @Test
    fun `edit user correctly`() = runBlocking {
        //Given
        val userModel = UserModel()
        coEvery { userRepository.updateUser(userModel) } returns Resource.Success()

        //When
        val result = editUserUseCase.invoke(userModel)

        //Then
        assert(result is Resource.Success)
    }

    @Test
    fun `edit user error`() = runBlocking {
        //Given
        val userModel = UserModel()
        coEvery { userRepository.updateUser(userModel) } returns Resource.Error("")

        //When
        val result = editUserUseCase.invoke(userModel)

        //Then
        assert(result is Resource.Error)
    }
}