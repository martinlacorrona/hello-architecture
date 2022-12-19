package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.domain.usecase.GetUserListUseCase
import kotlinx.coroutines.flow.Flow

class GetUserListUseCaseImpl(
    private val userListRepository: UserListRepository
) : GetUserListUseCase {
    override fun invoke(name: String): Flow<List<UserModel>> =
        userListRepository.getUserList(name)
}