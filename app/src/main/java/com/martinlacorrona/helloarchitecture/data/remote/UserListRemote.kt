package com.martinlacorrona.helloarchitecture.data.remote

import com.martinlacorrona.helloarchitecture.data.remote.model.UserDto
import retrofit2.Response
import retrofit2.http.GET

interface UserListRemote {
    @GET("api/User")
    suspend fun getUsers(): Response<List<UserDto>>
}