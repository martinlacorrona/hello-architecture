package com.martinlacorrona.helloarchitecture.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.usecase.CreateUserUseCase
import com.martinlacorrona.helloarchitecture.domain.usecase.FetchUserListUseCase
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreateUserViewModelTest {

    @RelaxedMockK
    private lateinit var createUserUseCase: CreateUserUseCase

    @RelaxedMockK
    private lateinit var fetchUserListUseCase: FetchUserListUseCase

    private lateinit var vm: CreateUserViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        vm = CreateUserViewModel(createUserUseCase, fetchUserListUseCase)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `view model create success`() = runTest {
        // Given vm with this use case
        val userModel = UserModel()
        coEvery { createUserUseCase.invoke(userModel) } returns Resource.Success()

        // When
        vm.createUser()

        // It
        assertEquals(false, vm.isLoadingStatus.value)
        assertEquals(true, vm.isDone.value)
        assertEquals(false, vm.isError.value)
    }

    @Test
    fun `view model create error`() = runTest {
        // Given
        val userModel = UserModel()
        coEvery { createUserUseCase.invoke(userModel) } returns Resource.Error("")

        // When
        vm.createUser()

        // It
        assertEquals(false, vm.isLoadingStatus.value)
        assertEquals(false, vm.isDone.value)
        assertEquals(true, vm.isError.value)
    }

    @Test
    fun `view model clean after error`() = runTest {
        // Given
        val userModel = UserModel()
        coEvery { createUserUseCase.invoke(userModel) } returns Resource.Error("")

        // When
        vm.createUser()
        vm.clearError()

        // It
        assertEquals(false, vm.isLoadingStatus.value)
        assertEquals(false, vm.isDone.value)
        assertEquals(false, vm.isError.value)
    }
}