package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import com.martinlacorrona.helloarchitecture.domain.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.domain.usecase.IsFetchingUserListUseCase
import kotlinx.coroutines.flow.Flow

class IsFetchingUserListUseCaseImpl(
    private val userListRepository: UserListRepository
) : IsFetchingUserListUseCase {
    override fun invoke(): Flow<Boolean> =
        userListRepository.isFetching()
}