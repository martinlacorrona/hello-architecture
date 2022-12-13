package com.martinlacorrona.helloarchitecture.data.impl

import com.martinlacorrona.helloarchitecture.domain.repository.UserRepository
import com.martinlacorrona.helloarchitecture.data.local.UserDao
import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.data.mapper.toUserDto
import com.martinlacorrona.helloarchitecture.data.remote.UserRemote
import com.martinlacorrona.helloarchitecture.data.remote.model.UserDto
import com.martinlacorrona.helloarchitecture.data.util.Status
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userRemote: UserRemote,
    private val databaseDispatcher: CoroutineDispatcher,
) : UserRepository {
    override fun createUser(userDto: UserDto): Flow<Status> = channelFlow {
        send(Status.LOADING)
        try {
            val response = userRemote.createUser(userDto)
            if (!response.isSuccessful) send(Status.ERROR)
            else {
                send(Status.SUCCESS)
            }
        } catch (e: Exception) {
            send(Status.ERROR)
        }
    }

    override fun updateUser(userEntity: UserEntity): Flow<Status> = channelFlow {
        send(Status.LOADING)
        try {
            val response = userRemote.updateUser(userEntity.toUserDto())
            withContext(databaseDispatcher) {
                if (!response.isSuccessful) send(Status.ERROR)
                else {
                    userDao.updateUser(userEntity)
                    send(Status.SUCCESS)
                }
            }
        } catch (e: Exception) {
            send(Status.ERROR)
        }
    }

    override fun removeUser(userRemoteId: Int): Flow<Status> = channelFlow {
        send(Status.LOADING)
        try {
            val response = userRemote.deleteUser(userRemoteId)
            withContext(databaseDispatcher) {
                if (!response.isSuccessful) send(Status.ERROR)
                else {
                    userDao.deleteUser(userRemoteId)
                    send(Status.SUCCESS)
                }
            }
        } catch (e: Exception) {
            send(Status.ERROR)
        }
    }
}