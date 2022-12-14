package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import com.martinlacorrona.helloarchitecture.data.mapper.toUserEntity
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.repository.UserRepository
import com.martinlacorrona.helloarchitecture.domain.usecase.EditUserUseCase
import com.martinlacorrona.helloarchitecture.domain.util.Resource

class EditUserUseCaseImpl(
    private val userRepository: UserRepository
) : EditUserUseCase {
    override suspend fun invoke(userModel: UserModel): Resource<Unit> =
        userRepository.updateUser(userModel.toUserEntity())
}