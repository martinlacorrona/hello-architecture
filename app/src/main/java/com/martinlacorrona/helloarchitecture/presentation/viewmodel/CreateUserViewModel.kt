package com.martinlacorrona.helloarchitecture.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.martinlacorrona.helloarchitecture.domain.model.StatusModel
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.usecase.CreateUserUseCase
import com.martinlacorrona.helloarchitecture.domain.usecase.FetchUserListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CreateUserViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val fetchUserListUseCase: FetchUserListUseCase
) : BaseUserDataViewModel() {

    fun createUser() {
        viewModelScope.launch {
            createUserUseCase.invoke(
                UserModel(
                    name = name.value ?: "",
                    birthday = birthday.value?.time ?: 0
                )
            )
                .collect {
                    setIsLoadingStatus(it == StatusModel.LOADING)
                    setIsDone(it == StatusModel.SUCCESS)
                    setIsError(it == StatusModel.ERROR)
                }
        }
    }

    fun fetchUserList(coroutineScope: CoroutineScope) {
        if (isLoadingStatus.value != true) {
            coroutineScope.launch {
                fetchUserListUseCase.invoke().collect {}
            }
        }
    }
}