package com.martinlacorrona.helloarchitecture.repository

import com.martinlacorrona.helloarchitecture.repository.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.repository.util.Status
import kotlinx.coroutines.flow.Flow

interface UserListRepository {
    fun fetchUserList(): Flow<Status>
    fun getUserList(name: String): Flow<List<UserEntity>>
}