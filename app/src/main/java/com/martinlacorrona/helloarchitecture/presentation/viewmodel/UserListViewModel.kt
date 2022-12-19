package com.martinlacorrona.helloarchitecture.presentation.viewmodel

import androidx.lifecycle.*
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.usecase.FetchUserListUseCase
import com.martinlacorrona.helloarchitecture.domain.usecase.GetUserListUseCase
import com.martinlacorrona.helloarchitecture.domain.usecase.IsFetchingUserListUseCase
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import kotlinx.coroutines.launch

class UserListViewModel(
    private val fetchUserListUseCase: FetchUserListUseCase,
    private val getUserListUseCase: GetUserListUseCase,
    private val isFetchingUserListUseCase: IsFetchingUserListUseCase
) : ViewModel() {

    private val _userList = MediatorLiveData<List<UserModel>>()
    val userList: LiveData<List<UserModel>> = _userList

    val isLoadingStatus: LiveData<Boolean> = isFetchingUserListUseCase.invoke().asLiveData()

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
                if (fetchUserListUseCase.invoke() is Resource.Error) {
                    _isError.postValue(true)
                }
            }
        }
    }

    fun clearError() {
        _isError.value = false
    }
}