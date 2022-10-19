package com.martinlacorrona.helloarchitecture.ui.viewmodel

import androidx.lifecycle.*
import com.martinlacorrona.helloarchitecture.ui.model.StatusModel
import com.martinlacorrona.helloarchitecture.ui.model.UserModel
import com.martinlacorrona.helloarchitecture.usecase.FetchUserListUseCase
import com.martinlacorrona.helloarchitecture.usecase.GetUserListUseCase
import kotlinx.coroutines.launch

class UserListViewModel(
    private val fetchUserListUseCase: FetchUserListUseCase,
    private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {

    private val _userList = MediatorLiveData<List<UserModel>>()
    val userList: LiveData<List<UserModel>> = _userList

    private val _isLoadingStatus = MediatorLiveData<Boolean>().apply { postValue(false) }
    val isLoadingStatus: LiveData<Boolean> = _isLoadingStatus

    private val _isError = MediatorLiveData<Boolean>().apply { postValue(false) }
    val isError: LiveData<Boolean> = _isError

    val searchValue = MutableLiveData<String>().apply { postValue("") }

    init {
        fetchUserList()

        _userList.addSource(searchValue) {
            getUserList(it)
        }
    }

    private fun getUserList(name: String) {
        viewModelScope.launch {
            getUserListUseCase.invoke(name)
                .collect { _userList.value = it }
        }
    }

    fun fetchUserList() {
        if (isLoadingStatus.value != true) {
            viewModelScope.launch {
                fetchUserListUseCase.invoke()
                    .collect {
                        _isLoadingStatus.value = it == StatusModel.LOADING
                        _isError.value = it == StatusModel.ERROR
                    }
            }
        }
    }

    fun clearError() {
        _isError.value = false
    }
}