package com.martinlacorrona.helloarchitecture.domain.usecase

import androidx.lifecycle.LiveData
import com.martinlacorrona.helloarchitecture.domain.model.UserModel

interface GetUserListUseCase {
    fun invoke(name: String): LiveData<List<UserModel>>
}