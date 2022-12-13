package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import com.martinlacorrona.helloarchitecture.domain.repository.UserRepository
import com.martinlacorrona.helloarchitecture.domain.model.StatusModel
import com.martinlacorrona.helloarchitecture.domain.usecase.DeleteUserUseCase
import com.martinlacorrona.helloarchitecture.data.mapper.toStatusModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeleteUserUseCaseImpl(
    private val userRepository: UserRepository
) : DeleteUserUseCase {
    override fun invoke(remoteId: Int): Flow<StatusModel> =
        userRepository.removeUser(remoteId)
            .map { it.toStatusModel() }
}