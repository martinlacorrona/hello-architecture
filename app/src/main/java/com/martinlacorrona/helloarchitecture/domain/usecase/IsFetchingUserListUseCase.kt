package com.martinlacorrona.helloarchitecture.domain.usecase

import androidx.lifecycle.LiveData

interface IsFetchingUserListUseCase {
    fun invoke(): LiveData<Boolean>
}