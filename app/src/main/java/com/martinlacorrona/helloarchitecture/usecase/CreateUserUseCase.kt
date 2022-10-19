package com.martinlacorrona.helloarchitecture.usecase

import com.martinlacorrona.helloarchitecture.ui.model.StatusModel
import com.martinlacorrona.helloarchitecture.ui.model.UserModel
import kotlinx.coroutines.flow.Flow

interface CreateUserUseCase {
    fun invoke(userModel: UserModel): Flow<StatusModel>
}