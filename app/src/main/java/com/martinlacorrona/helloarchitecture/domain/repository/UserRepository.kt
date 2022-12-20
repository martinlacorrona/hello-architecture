package com.martinlacorrona.helloarchitecture.domain.repository

import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.util.Resource

interface UserRepository : BaseRepository {
    suspend fun createUser(userModel: UserModel): Resource<Unit>
    suspend fun updateUser(userModel: UserModel): Resource<Unit>
    suspend fun removeUser(userRemoteId: Int): Resource<Unit>
}