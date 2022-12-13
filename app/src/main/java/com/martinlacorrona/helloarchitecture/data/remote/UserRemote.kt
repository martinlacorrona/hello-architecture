package com.martinlacorrona.helloarchitecture.data.remote

import com.martinlacorrona.helloarchitecture.data.remote.model.UserDto
import retrofit2.Response
import retrofit2.http.*

interface UserRemote {
    @Headers("Content-Type: application/json")
    @POST("api/User")
    suspend fun createUser(
        @Body userData: UserDto
    ): Response<Void>

    @Headers("Content-Type: application/json")
    @PUT("api/User")
    suspend fun updateUser(
        @Body userData: UserDto
    ): Response<Void>

    @DELETE("api/User/{id}")
    suspend fun deleteUser(
        @Path("id") id: Int
    ): Response<Void>
}