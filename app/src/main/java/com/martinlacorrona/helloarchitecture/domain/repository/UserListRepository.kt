package com.martinlacorrona.helloarchitecture.domain.repository

import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserListRepository : BaseRepository {
    suspend fun fetchUserList(): Resource<Unit>
    fun isFetching(): Flow<Boolean>
    fun getUserList(name: String): Flow<List<UserModel>>
}