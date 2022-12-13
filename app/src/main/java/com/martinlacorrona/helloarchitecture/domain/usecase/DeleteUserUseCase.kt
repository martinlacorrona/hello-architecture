package com.martinlacorrona.helloarchitecture.domain.usecase

import com.martinlacorrona.helloarchitecture.domain.model.StatusModel
import kotlinx.coroutines.flow.Flow

interface DeleteUserUseCase {
    fun invoke(remoteId: Int): Flow<StatusModel>
}