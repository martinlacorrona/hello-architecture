package com.martinlacorrona.helloarchitecture.domain.usecase

import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.util.Resource

interface EditUserUseCase {
    suspend fun invoke(userModel: UserModel): Resource<Unit>
}