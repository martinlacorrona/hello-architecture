package com.martinlacorrona.helloarchitecture.usecase

import com.martinlacorrona.helloarchitecture.ui.model.UserModel
import kotlinx.coroutines.flow.Flow

interface GetUserListUseCase {
    fun invoke(name: String): Flow<List<UserModel>>
}