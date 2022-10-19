package com.martinlacorrona.helloarchitecture.usecase.impl

import com.martinlacorrona.helloarchitecture.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.ui.model.StatusModel
import com.martinlacorrona.helloarchitecture.usecase.FetchUserListUseCase
import com.martinlacorrona.helloarchitecture.usecase.mapper.StatusModelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchUserListUseCaseImpl(
    private val userListRepository: UserListRepository
) : FetchUserListUseCase {
    override fun invoke(): Flow<StatusModel> =
        userListRepository.fetchUserList()
            .map { StatusModelMapper.mapToStatusModel(it) }
}