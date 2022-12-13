package com.martinlacorrona.helloarchitecture.domain.repository

import androidx.lifecycle.LiveData
import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.data.util.Status
import kotlinx.coroutines.flow.Flow

interface UserListRepository {
    fun fetchUserList(): Flow<Status>
    fun isFetching(): LiveData<Boolean>
    fun getUserList(name: String): Flow<List<UserEntity>>
}