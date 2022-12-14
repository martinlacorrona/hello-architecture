package com.martinlacorrona.helloarchitecture.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.martinlacorrona.helloarchitecture.domain.usecase.FetchUserListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

open class BaseUserDataViewModel(
    private val fetchUserListUseCase: FetchUserListUseCase
) : ViewModel() {

    val name = MutableLiveData<String>()
    val birthday = MutableLiveData<Date>()

    private val _isLoadingStatus = MediatorLiveData<Boolean>().apply { postValue(false) }
    val isLoadingStatus: LiveData<Boolean> = _isLoadingStatus

    private val _isError = MediatorLiveData<Boolean>().apply { postValue(false) }
    val isError: LiveData<Boolean> = _isError

    private val _isDone = MediatorLiveData<Boolean>().apply { postValue(false) }
    val isDone: LiveData<Boolean> = _isDone

    open fun setIsLoadingStatus(value: Boolean) {
        _isLoadingStatus.postValue(value)
    }

    open fun setIsError(value: Boolean) {
        _isError.postValue(value)
    }

    open fun setIsDone(value: Boolean) {
        _isDone.postValue(value)
    }

    fun clearError() {
        _isError.value = false
    }

    fun fetchUserList(coroutineScope: CoroutineScope) {
        if (isLoadingStatus.value != true) {
            coroutineScope.launch {
                fetchUserListUseCase.invoke()
            }
        }
    }
}