package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import com.martinlacorrona.helloarchitecture.data.mapper.toStatusModel
import com.martinlacorrona.helloarchitecture.data.mapper.toUserEntity
import com.martinlacorrona.helloarchitecture.domain.repository.UserRepository
import com.martinlacorrona.helloarchitecture.domain.model.StatusModel
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.usecase.EditUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EditUserUseCaseImpl(
    private val userRepository: UserRepository
) : EditUserUseCase {
    override fun invoke(userModel: UserModel): Flow<StatusModel> =
        userRepository.updateUser(userModel.toUserEntity())
            .map { it.toStatusModel() }
}