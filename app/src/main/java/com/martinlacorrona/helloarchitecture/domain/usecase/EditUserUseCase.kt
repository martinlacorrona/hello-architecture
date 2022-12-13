package com.martinlacorrona.helloarchitecture.domain.usecase

import com.martinlacorrona.helloarchitecture.domain.model.StatusModel
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface EditUserUseCase {
    fun invoke(userModel: UserModel): Flow<StatusModel>
}