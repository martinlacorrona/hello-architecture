package com.martinlacorrona.helloarchitecture.domain.usecase

import com.martinlacorrona.helloarchitecture.domain.util.Resource

interface FetchUserListUseCase {
    suspend fun invoke(): Resource<Unit>
}