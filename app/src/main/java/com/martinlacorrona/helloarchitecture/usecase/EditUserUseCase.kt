package com.martinlacorrona.helloarchitecture.usecase

import com.martinlacorrona.helloarchitecture.ui.model.StatusModel
import com.martinlacorrona.helloarchitecture.ui.model.UserModel
import kotlinx.coroutines.flow.Flow

interface EditUserUseCase {
    fun invoke(userModel: UserModel): Flow<StatusModel>
}