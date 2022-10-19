package com.martinlacorrona.helloarchitecture.repository

import com.martinlacorrona.helloarchitecture.repository.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.repository.remote.model.UserRemoteEntity
import com.martinlacorrona.helloarchitecture.repository.util.Status
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun createUser(userRemoteEntity: UserRemoteEntity): Flow<Status>
    fun updateUser(userEntity: UserEntity): Flow<Status>
    fun removeUser(userRemoteId: Int): Flow<Status>
}