package com.martinlacorrona.helloarchitecture.usecase.impl

import com.martinlacorrona.helloarchitecture.repository.UserRepository
import com.martinlacorrona.helloarchitecture.ui.model.StatusModel
import com.martinlacorrona.helloarchitecture.ui.model.UserModel
import com.martinlacorrona.helloarchitecture.usecase.EditUserUseCase
import com.martinlacorrona.helloarchitecture.usecase.mapper.StatusModelMapper
import com.martinlacorrona.helloarchitecture.usecase.mapper.UserModelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EditUserUseCaseImpl(
    private val userRepository: UserRepository
) : EditUserUseCase {
    override fun invoke(userModel: UserModel): Flow<StatusModel> =
        userRepository.updateUser(UserModelMapper.mapToUserEntity(userModel))
            .map { StatusModelMapper.mapToStatusModel(it) }
}