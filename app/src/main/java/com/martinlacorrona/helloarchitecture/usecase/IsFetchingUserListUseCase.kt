package com.martinlacorrona.helloarchitecture.usecase

import androidx.lifecycle.LiveData

interface IsFetchingUserListUseCase {
    fun invoke(): LiveData<Boolean>
}