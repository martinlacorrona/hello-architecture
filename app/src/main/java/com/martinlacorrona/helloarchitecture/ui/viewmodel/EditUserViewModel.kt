package com.martinlacorrona.helloarchitecture.ui.viewmodel

import androidx.lifecycle.*
import com.martinlacorrona.helloarchitecture.ui.model.StatusModel
import com.martinlacorrona.helloarchitecture.ui.model.UserModel
import com.martinlacorrona.helloarchitecture.usecase.DeleteUserUseCase
import com.martinlacorrona.helloarchitecture.usecase.EditUserUseCase
import com.martinlacorrona.helloarchitecture.usecase.FetchUserListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

class EditUserViewModel(
    private val editUserUseCase: EditUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val fetchUserListUseCase: FetchUserListUseCase
) : ViewModel() {

    var id = 0
    var remoteId = 0
    val name = MutableLiveData<String>()
    val birthday = MutableLiveData<Date>()

    private val _isLoadingStatus = MediatorLiveData<Boolean>().apply { postValue(false) }
    val isLoadingStatus: LiveData<Boolean> = _isLoadingStatus

    private val _isError = MediatorLiveData<Boolean>().apply { postValue(false) }
    val isError: LiveData<Boolean> = _isError

    private val _isDone = MediatorLiveData<Boolean>().apply { postValue(false) }
    val isDone: LiveData<Boolean> = _isDone

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
                    _isLoadingStatus.value = it == StatusModel.LOADING
                    _isDone.value = it == StatusModel.SUCCESS
                    _isError.value = it == StatusModel.ERROR
                }
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            deleteUserUseCase.invoke(remoteId)
                .collect {
                    _isLoadingStatus.value = it == StatusModel.LOADING
                    _isDone.value = it == StatusModel.SUCCESS
                    _isError.value = it == StatusModel.ERROR
                }
        }
    }

    fun clearError() {
        _isError.value = false
    }

    fun fetchUserList(coroutineScope: CoroutineScope) {
        if (isLoadingStatus.value != true) {
            coroutineScope.launch {
                fetchUserListUseCase.invoke().collect {}
            }
        }
    }
}