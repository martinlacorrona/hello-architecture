package com.martinlacorrona.helloarchitecture.domain.usecase

import kotlinx.coroutines.flow.Flow

interface IsFetchingUserListUseCase {
    fun invoke(): Flow<Boolean>
}