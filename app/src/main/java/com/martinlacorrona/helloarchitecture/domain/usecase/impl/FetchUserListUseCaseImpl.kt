package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import com.martinlacorrona.helloarchitecture.data.mapper.toStatusModel
import com.martinlacorrona.helloarchitecture.domain.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.domain.model.StatusModel
import com.martinlacorrona.helloarchitecture.domain.usecase.FetchUserListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchUserListUseCaseImpl(
    private val userListRepository: UserListRepository
) : FetchUserListUseCase {
    override fun invoke(): Flow<StatusModel> =
        userListRepository.fetchUserList()
            .map { it.toStatusModel() }
}