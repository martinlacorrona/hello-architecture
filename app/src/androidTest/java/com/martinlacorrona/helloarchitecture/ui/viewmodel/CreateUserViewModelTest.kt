package com.martinlacorrona.helloarchitecture.ui.viewmodel

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.martinlacorrona.helloarchitecture.ui.model.StatusModel
import com.martinlacorrona.helloarchitecture.ui.model.UserModel
import com.martinlacorrona.helloarchitecture.usecase.CreateUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class CreateUserViewModelTest {

    @Test
    fun viewModelTestLoading() = runTest {
        // Given vm with this use case
        val useCaseDummy = object : CreateUserUseCase {
            override fun invoke(userModel: UserModel) = flow { emit(StatusModel.LOADING) }
        }
        val vm = CreateUserViewModel(useCaseDummy)

        // When try to create user
        vm.createUser()

        // It's loading
        wait1Second()
        assertEquals(true, vm.isLoadingStatus.value)
        assertEquals(false, vm.isDone.value)
        assertEquals(false, vm.isError.value)
    }

    @Test
    fun viewModelTestSuccess() = runTest {
        // Given vm with this use case
        val useCaseDummy = object : CreateUserUseCase {
            override fun invoke(userModel: UserModel) = flow { emit(StatusModel.SUCCESS) }
        }
        val vm = CreateUserViewModel(useCaseDummy)

        // When try to create user
        vm.createUser()

        // It's done
        wait1Second()
        assertEquals(false, vm.isLoadingStatus.value)
        assertEquals(true, vm.isDone.value)
        assertEquals(false, vm.isError.value)
    }

    @Test
    fun viewModelTestError() = runTest {
        // Given vm with this use case
        val useCaseDummy = object : CreateUserUseCase {
            override fun invoke(userModel: UserModel) = flow { emit(StatusModel.ERROR) }
        }
        val vm = CreateUserViewModel(useCaseDummy)

        // When try to create user
        vm.createUser()

        // It's an error
        wait1Second()
        assertEquals(false, vm.isLoadingStatus.value)
        assertEquals(false, vm.isDone.value)
        assertEquals(true, vm.isError.value)
    }

    private fun wait1Second() {
        Thread.sleep(1000)
    }
}