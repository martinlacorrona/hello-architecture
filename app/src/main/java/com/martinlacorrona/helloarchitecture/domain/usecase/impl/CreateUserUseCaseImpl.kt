package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import com.martinlacorrona.helloarchitecture.data.mapper.toStatusModel
import com.martinlacorrona.helloarchitecture.data.mapper.toUserDto
import com.martinlacorrona.helloarchitecture.domain.repository.UserRepository
import com.martinlacorrona.helloarchitecture.domain.model.StatusModel
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.usecase.CreateUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CreateUserUseCaseImpl(
    private val userRepository: UserRepository
) : CreateUserUseCase {
    override fun invoke(userModel: UserModel): Flow<StatusModel> =
        userRepository.createUser(userModel.toUserDto())
            .map { it.toStatusModel() }
}