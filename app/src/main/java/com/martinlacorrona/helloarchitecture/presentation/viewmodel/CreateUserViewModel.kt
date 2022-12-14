package com.martinlacorrona.helloarchitecture.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.usecase.CreateUserUseCase
import com.martinlacorrona.helloarchitecture.domain.usecase.FetchUserListUseCase
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateUserViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val fetchUserListUseCase: FetchUserListUseCase,
) : BaseUserDataViewModel() {

    fun createUser() {
        viewModelScope.launch {
            setIsLoadingStatus(true)
            when (createUserUseCase.invoke(
                UserModel(
                    name = name.value ?: "",
                    birthday = birthday.value?.time ?: 0
                )
            )) {
                is Resource.Success -> setIsDone(true)
                is Resource.Error -> setIsError(true)
            }
            setIsLoadingStatus(false)
        }
    }

    fun fetchUserList(coroutineScope: CoroutineScope) {
        if (isLoadingStatus.value != true) {
            coroutineScope.launch {
                fetchUserListUseCase.invoke()
            }
        }
    }
}