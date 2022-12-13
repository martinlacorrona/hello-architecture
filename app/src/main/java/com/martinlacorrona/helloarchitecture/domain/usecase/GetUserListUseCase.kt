package com.martinlacorrona.helloarchitecture.domain.usecase

import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface GetUserListUseCase {
    fun invoke(name: String): Flow<List<UserModel>>
}