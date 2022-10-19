package com.martinlacorrona.helloarchitecture.usecase.impl

import com.martinlacorrona.helloarchitecture.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.ui.model.UserModel
import com.martinlacorrona.helloarchitecture.usecase.GetUserListUseCase
import com.martinlacorrona.helloarchitecture.usecase.mapper.UserModelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUserListUseCaseImpl(
    private val userListRepository: UserListRepository
) : GetUserListUseCase {
    override fun invoke(name: String): Flow<List<UserModel>> =
        userListRepository.getUserList(name)
            .map { userList -> userList.map { UserModelMapper.mapToUserModel(it) } }
}