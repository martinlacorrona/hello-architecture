package com.martinlacorrona.helloarchitecture.domain.repository

import androidx.lifecycle.LiveData
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.util.Resource

interface UserListRepository : BaseRepository {
    suspend fun fetchUserList(): Resource<Unit>
    fun isFetching(): LiveData<Boolean>
    fun getUserList(name: String): LiveData<List<UserModel>>
}