package com.martinlacorrona.helloarchitecture.repository.remote

import com.martinlacorrona.helloarchitecture.repository.remote.model.UserRemoteEntity
import retrofit2.Response
import retrofit2.http.GET

interface UserListRemote {
    @GET("api/User")
    suspend fun getUsers(): Response<List<UserRemoteEntity>>
}