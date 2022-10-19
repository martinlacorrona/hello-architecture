package com.martinlacorrona.helloarchitecture.usecase

import com.martinlacorrona.helloarchitecture.ui.model.StatusModel
import kotlinx.coroutines.flow.Flow

interface FetchUserListUseCase {
    fun invoke(): Flow<StatusModel>
}