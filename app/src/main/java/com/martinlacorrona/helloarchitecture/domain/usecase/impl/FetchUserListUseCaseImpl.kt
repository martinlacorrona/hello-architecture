package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import com.martinlacorrona.helloarchitecture.domain.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.domain.usecase.FetchUserListUseCase
import com.martinlacorrona.helloarchitecture.domain.util.Resource

class FetchUserListUseCaseImpl(
    private val userListRepository: UserListRepository
) : FetchUserListUseCase {
    override suspend fun invoke(): Resource<Unit> =
        userListRepository.fetchUserList()
}