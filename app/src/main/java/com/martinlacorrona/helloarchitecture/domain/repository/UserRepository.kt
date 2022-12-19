package com.martinlacorrona.helloarchitecture.domain.repository

import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import retrofit2.Response

interface UserRepository : BaseRepository {
    suspend fun createUser(userModel: UserModel): Resource<Unit>
    suspend fun updateUser(userModel: UserModel): Resource<Unit>
    suspend fun removeUser(userRemoteId: Int): Resource<Unit>

    suspend fun onResponse(response: Response<Void>, onSuccess: suspend () -> Unit = {}): Resource<Unit> =
        if (!response.isSuccessful) Resource.Error(BaseRepository.RESPONSE_NOT_SUCCESSFUL_MESSAGE)
        else {
            onSuccess()
            Resource.Success()
        }
}