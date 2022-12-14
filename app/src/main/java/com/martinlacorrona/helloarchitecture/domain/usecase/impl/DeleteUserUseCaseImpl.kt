package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import com.martinlacorrona.helloarchitecture.domain.repository.UserRepository
import com.martinlacorrona.helloarchitecture.domain.usecase.DeleteUserUseCase
import com.martinlacorrona.helloarchitecture.domain.util.Resource

class DeleteUserUseCaseImpl(
    private val userRepository: UserRepository
) : DeleteUserUseCase {
    override suspend fun invoke(remoteId: Int): Resource<Unit> =
        userRepository.removeUser(remoteId)
}