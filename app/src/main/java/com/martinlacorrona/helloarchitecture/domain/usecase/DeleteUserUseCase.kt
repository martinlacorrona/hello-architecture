package com.martinlacorrona.helloarchitecture.domain.usecase

import com.martinlacorrona.helloarchitecture.domain.util.Resource

interface DeleteUserUseCase {
    suspend fun invoke(remoteId: Int): Resource<Unit>
}