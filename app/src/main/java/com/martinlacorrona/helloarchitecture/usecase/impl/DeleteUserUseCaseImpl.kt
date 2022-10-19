package com.martinlacorrona.helloarchitecture.usecase.impl

import com.martinlacorrona.helloarchitecture.repository.UserRepository
import com.martinlacorrona.helloarchitecture.ui.model.StatusModel
import com.martinlacorrona.helloarchitecture.usecase.DeleteUserUseCase
import com.martinlacorrona.helloarchitecture.usecase.mapper.StatusModelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeleteUserUseCaseImpl(
    private val userRepository: UserRepository
) : DeleteUserUseCase {
    override fun invoke(remoteId: Int): Flow<StatusModel> =
        userRepository.removeUser(remoteId)
            .map { StatusModelMapper.mapToStatusModel(it) }
}