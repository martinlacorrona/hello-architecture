package com.martinlacorrona.helloarchitecture.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.usecase.DeleteUserUseCase
import com.martinlacorrona.helloarchitecture.domain.usecase.EditUserUseCase
import com.martinlacorrona.helloarchitecture.domain.usecase.FetchUserListUseCase
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import kotlinx.coroutines.launch

class EditUserViewModel(
    private val editUserUseCase: EditUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    fetchUserListUseCase: FetchUserListUseCase
) : BaseUserDataViewModel(fetchUserListUseCase) {

    var id = 0
    var remoteId = 0

    fun updateUser() {
        viewModelScope.launch {
            setIsLoadingStatus(true)
            when (editUserUseCase.invoke(
                UserModel(
                    id,
                    remoteId,
                    name.value ?: "",
                    birthday.value?.time ?: 0
                )
            )) {
                is Resource.Success -> setIsDone(true)
                is Resource.Error -> setIsError(true)
            }
            setIsLoadingStatus(false)
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            setIsLoadingStatus(true)
            when (deleteUserUseCase.invoke(remoteId)) {
                is Resource.Success -> setIsDone(true)
                is Resource.Error -> setIsError(true)
            }
            setIsLoadingStatus(false)
        }
    }
}