package com.martinlacorrona.helloarchitecture.data.repository

import com.martinlacorrona.helloarchitecture.data.local.UserDao
import com.martinlacorrona.helloarchitecture.data.mapper.toUserDto
import com.martinlacorrona.helloarchitecture.data.mapper.toUserEntity
import com.martinlacorrona.helloarchitecture.data.remote.UserRemote
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.repository.UserRepository
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userRemote: UserRemote,
    private val databaseDispatcher: CoroutineDispatcher,
) : UserRepository {
    override suspend fun createUser(userModel: UserModel): Resource<Unit> {
        return try {
            val response = userRemote.createUser(userModel.toUserDto())
            onResponse(response)
        } catch (e: Exception) {
            exceptionError(e)
        }
    }

    override suspend fun updateUser(userModel: UserModel): Resource<Unit> {
        return try {
            val response = userRemote.updateUser(userModel.toUserDto())
            withContext(databaseDispatcher) {
                onResponse(response) { userDao.updateUser(userModel.toUserEntity()) }
            }
        } catch (e: Exception) {
            exceptionError(e)
        }
    }

    override suspend fun removeUser(userRemoteId: Int): Resource<Unit> {
        return try {
            val response = userRemote.deleteUser(userRemoteId)
            withContext(databaseDispatcher) {
                onResponse(response) { userDao.deleteUser(userRemoteId) }
            }
        } catch (e: Exception) {
            exceptionError(e)
        }
    }
}