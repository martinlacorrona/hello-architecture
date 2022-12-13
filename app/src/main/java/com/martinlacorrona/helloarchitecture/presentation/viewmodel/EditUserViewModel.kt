package com.martinlacorrona.helloarchitecture.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.martinlacorrona.helloarchitecture.domain.model.StatusModel
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.usecase.DeleteUserUseCase
import com.martinlacorrona.helloarchitecture.domain.usecase.EditUserUseCase
import com.martinlacorrona.helloarchitecture.domain.usecase.FetchUserListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class EditUserViewModel(
    private val editUserUseCase: EditUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val fetchUserListUseCase: FetchUserListUseCase
) : BaseUserDataViewModel() {

    var id = 0
    var remoteId = 0

    fun updateUser() {
        viewModelScope.launch {
            editUserUseCase.invoke(
                UserModel(
                    id,
                    remoteId,
                    name.value ?: "",
                    birthday.value?.time ?: 0
                )
            )
                .collect {
                    setIsLoadingStatus(it == StatusModel.LOADING)
                    setIsDone(it == StatusModel.SUCCESS)
                    setIsError(it == StatusModel.ERROR)
                }
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            deleteUserUseCase.invoke(remoteId)
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