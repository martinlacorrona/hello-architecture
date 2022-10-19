package com.martinlacorrona.helloarchitecture.repository.impl

import com.martinlacorrona.helloarchitecture.repository.UserRepository
import com.martinlacorrona.helloarchitecture.repository.local.UserDao
import com.martinlacorrona.helloarchitecture.repository.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.repository.mapper.UserMapper
import com.martinlacorrona.helloarchitecture.repository.remote.UserRemote
import com.martinlacorrona.helloarchitecture.repository.remote.model.UserRemoteEntity
import com.martinlacorrona.helloarchitecture.repository.util.Status
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userRemote: UserRemote,
    private val databaseDispatcher: CoroutineDispatcher,
) : UserRepository {
    override fun createUser(userRemoteEntity: UserRemoteEntity): Flow<Status> = channelFlow {
        send(Status.LOADING)
        try {
            val response = userRemote.createUser(userRemoteEntity)
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
            val response = userRemote.updateUser(UserMapper.mapEntityToResponse(userEntity))
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