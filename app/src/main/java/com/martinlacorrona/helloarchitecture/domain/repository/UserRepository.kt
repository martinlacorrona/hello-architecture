package com.martinlacorrona.helloarchitecture.domain.repository

import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.data.remote.model.UserDto
import com.martinlacorrona.helloarchitecture.data.util.Status
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun createUser(userDto: UserDto): Flow<Status>
    fun updateUser(userEntity: UserEntity): Flow<Status>
    fun removeUser(userRemoteId: Int): Flow<Status>
}