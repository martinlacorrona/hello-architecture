package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.domain.usecase.GetUserListUseCase

class GetUserListUseCaseImpl(
    private val userListRepository: UserListRepository
) : GetUserListUseCase {
    override fun invoke(name: String): LiveData<List<UserModel>> =
        userListRepository.getUserList(name).asLiveData()
}