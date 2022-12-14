package com.martinlacorrona.helloarchitecture.data.repository

import com.martinlacorrona.helloarchitecture.data.local.UserDao
import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.data.mapper.toUserDto
import com.martinlacorrona.helloarchitecture.data.remote.UserRemote
import com.martinlacorrona.helloarchitecture.data.remote.model.UserDto
import com.martinlacorrona.helloarchitecture.domain.repository.BaseRepository.Companion.RESPONSE_NOT_SUCCESSFUL_MESSAGE
import com.martinlacorrona.helloarchitecture.domain.repository.BaseRepository.Companion.UNKNOWN_ERROR_MESSAGE
import com.martinlacorrona.helloarchitecture.domain.repository.UserRepository
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userRemote: UserRemote,
    private val databaseDispatcher: CoroutineDispatcher,
) : UserRepository {
    override suspend fun createUser(userDto: UserDto): Resource<Unit> {
        return try {
            val response = userRemote.createUser(userDto)
            if (!response.isSuccessful) Resource.Error(RESPONSE_NOT_SUCCESSFUL_MESSAGE)
            else {
                Resource.Success()
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: UNKNOWN_ERROR_MESSAGE)
        }
    }

    override suspend fun updateUser(userEntity: UserEntity): Resource<Unit> {
        return try {
            val response = userRemote.updateUser(userEntity.toUserDto())
            withContext(databaseDispatcher) {
                if (!response.isSuccessful) Resource.Error(RESPONSE_NOT_SUCCESSFUL_MESSAGE)
                else {
                    userDao.updateUser(userEntity)
                    Resource.Success()
                }
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: UNKNOWN_ERROR_MESSAGE)
        }
    }

    override suspend fun removeUser(userRemoteId: Int): Resource<Unit> {
        return try {
            val response = userRemote.deleteUser(userRemoteId)
            withContext(databaseDispatcher) {
                if (!response.isSuccessful) Resource.Error(RESPONSE_NOT_SUCCESSFUL_MESSAGE)
                else {
                    userDao.deleteUser(userRemoteId)
                    Resource.Success()
                }
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: UNKNOWN_ERROR_MESSAGE)
        }
    }
}