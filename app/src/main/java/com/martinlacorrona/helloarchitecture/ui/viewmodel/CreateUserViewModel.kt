package com.martinlacorrona.helloarchitecture.ui.viewmodel

import androidx.lifecycle.*
import com.martinlacorrona.helloarchitecture.ui.model.StatusModel
import com.martinlacorrona.helloarchitecture.ui.model.UserModel
import com.martinlacorrona.helloarchitecture.usecase.CreateUserUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CreateUserViewModel(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    val name = MutableLiveData<String>().apply { postValue("") }
    val birthday = MutableLiveData<Date>()

    private val _isLoadingStatus = MediatorLiveData<Boolean>().apply { postValue(false) }
    val isLoadingStatus: LiveData<Boolean> = _isLoadingStatus

    private val _isError = MediatorLiveData<Boolean>().apply { postValue(false) }
    val isError: LiveData<Boolean> = _isError

    private val _isDone = MediatorLiveData<Boolean>().apply { postValue(false) }
    val isDone: LiveData<Boolean> = _isDone

    fun createUser() {
        viewModelScope.launch {
            createUserUseCase.invoke(
                UserModel(
                    name = name.value ?: "",
                    birthday = birthday.value?.time ?: 0
                )
            )
                .collect {
                    _isLoadingStatus.postValue(it == StatusModel.LOADING)
                    _isDone.postValue(it == StatusModel.SUCCESS)
                    _isError.postValue(it == StatusModel.ERROR)
                }
        }
    }

    fun clearError() {
        _isError.postValue(false)
    }
}