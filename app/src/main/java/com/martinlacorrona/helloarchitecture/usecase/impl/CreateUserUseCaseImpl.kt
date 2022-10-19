package com.martinlacorrona.helloarchitecture.usecase.impl

import com.martinlacorrona.helloarchitecture.repository.UserRepository
import com.martinlacorrona.helloarchitecture.ui.model.StatusModel
import com.martinlacorrona.helloarchitecture.ui.model.UserModel
import com.martinlacorrona.helloarchitecture.usecase.CreateUserUseCase
import com.martinlacorrona.helloarchitecture.usecase.mapper.StatusModelMapper
import com.martinlacorrona.helloarchitecture.usecase.mapper.UserModelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CreateUserUseCaseImpl(
    private val userRepository: UserRepository
) : CreateUserUseCase {
    override fun invoke(userModel: UserModel): Flow<StatusModel> =
        userRepository.createUser(UserModelMapper.mapToNewRemoteEntity(userModel))
            .map { StatusModelMapper.mapToStatusModel(it) }
}