package com.martinlacorrona.helloarchitecture.domain.repository

import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.data.remote.model.UserDto
import com.martinlacorrona.helloarchitecture.domain.util.Resource

interface UserRepository : BaseRepository {
    suspend fun createUser(userDto: UserDto): Resource<Unit>
    suspend fun updateUser(userEntity: UserEntity): Resource<Unit>
    suspend fun removeUser(userRemoteId: Int): Resource<Unit>
}