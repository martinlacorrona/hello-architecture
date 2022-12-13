package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import androidx.lifecycle.LiveData
import com.martinlacorrona.helloarchitecture.domain.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.domain.usecase.IsFetchingUserListUseCase

class IsFetchingUserListUseCaseImpl(
    private val userListRepository: UserListRepository
) : IsFetchingUserListUseCase {
    override fun invoke(): LiveData<Boolean> =
        userListRepository.isFetching()
}