package com.martinlacorrona.helloarchitecture.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.usecase.CreateUserUseCase
import com.martinlacorrona.helloarchitecture.domain.usecase.FetchUserListUseCase
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class CreateUserViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Test
    fun viewModelTestLoading() = runTest {
        // Given vm with this use case
        val useCaseDummy = object : CreateUserUseCase {
            override suspend fun invoke(userModel: UserModel): Resource<Unit> = runBlocking {
                //Wait two more times to state in loading
                wait1ms()
                wait1ms()
                Resource.Success()
            }
        }
        val fetchUseCaseDummy = object : FetchUserListUseCase {
            override suspend fun invoke(): Resource<Unit> = Resource.Success()
        }
        val vm = CreateUserViewModel(useCaseDummy, fetchUseCaseDummy)

        // When try to create user
        vm.createUser()

        // It's loading
        wait1ms()
        assertEquals(true, vm.isLoadingStatus.value)
        assertEquals(false, vm.isDone.value)
        assertEquals(false, vm.isError.value)
    }

    @Test
    fun viewModelTestSuccess() = runTest() {
        // Given vm with this use case
        val useCaseDummy = object : CreateUserUseCase {
            override suspend fun invoke(userModel: UserModel): Resource<Unit> = Resource.Success()
        }
        val fetchUseCaseDummy = object : FetchUserListUseCase {
            override suspend fun invoke(): Resource<Unit> = Resource.Success()
        }
        val vm = CreateUserViewModel(useCaseDummy, fetchUseCaseDummy)

        // When try to create user
        vm.createUser()

        // It's done
        wait1ms()
        assertEquals(false, vm.isLoadingStatus.value)
        assertEquals(true, vm.isDone.value)
        assertEquals(false, vm.isError.value)
    }

    @Test
    fun viewModelTestError() = runTest {
        // Given vm with this use case
        val useCaseDummy = object : CreateUserUseCase {
            override suspend fun invoke(userModel: UserModel): Resource<Unit> = Resource.Error("")
        }
        val fetchUseCaseDummy = object : FetchUserListUseCase {
            override suspend fun invoke(): Resource<Unit> = Resource.Success()
        }
        val vm = CreateUserViewModel(useCaseDummy, fetchUseCaseDummy)

        // When try to create user
        vm.createUser()

        // It's an error
        wait1ms()
        assertEquals(false, vm.isLoadingStatus.value)
        assertEquals(false, vm.isDone.value)
        assertEquals(true, vm.isError.value)
    }

    private fun wait1ms() {
        Thread.sleep(1)
    }
}