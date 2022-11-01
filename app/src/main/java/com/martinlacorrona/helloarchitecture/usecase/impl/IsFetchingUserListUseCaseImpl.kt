package com.martinlacorrona.helloarchitecture.usecase.impl

import androidx.lifecycle.LiveData
import com.martinlacorrona.helloarchitecture.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.usecase.IsFetchingUserListUseCase

class IsFetchingUserListUseCaseImpl(
    private val userListRepository: UserListRepository
) : IsFetchingUserListUseCase {
    override fun invoke(): LiveData<Boolean> =
        userListRepository.isFetching()
}