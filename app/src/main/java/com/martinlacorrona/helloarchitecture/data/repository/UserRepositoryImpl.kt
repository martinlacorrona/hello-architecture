package com.martinlacorrona.helloarchitecture.data.repository

import com.martinlacorrona.helloarchitecture.data.local.UserDao
import com.martinlacorrona.helloarchitecture.data.mapper.toUserDto
import com.martinlacorrona.helloarchitecture.data.mapper.toUserEntity
import com.martinlacorrona.helloarchitecture.data.remote.UserRemote
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.repository.BaseRepository
import com.martinlacorrona.helloarchitecture.domain.repository.UserRepository
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userRemote: UserRemote,
    private val databaseDispatcher: CoroutineDispatcher,
) : UserRepository {
    override suspend fun createUser(userModel: UserModel): Resource<Unit> =
        invoke({
            userRemote.createUser(userModel.toUserDto())
        })

    override suspend fun updateUser(userModel: UserModel): Resource<Unit> =
        invoke({
            userRemote.updateUser(userModel.toUserDto())
        }) {
            userDao.updateUser(userModel.toUserEntity())
        }

    override suspend fun removeUser(userRemoteId: Int): Resource<Unit> =
        invoke({
            userRemote.deleteUser(userRemoteId)
        }) {
            userDao.deleteUser(userRemoteId)
        }

    private suspend fun invoke(
        responseCall: suspend () -> Response<Void>,
        onSuccess: suspend () -> Unit = {}
    )
    : Resource<Unit> {
        return try {
            val response = responseCall()
            withContext(databaseDispatcher) {
                if (!response.isSuccessful) {
                    Resource.Error(BaseRepository.RESPONSE_NOT_SUCCESSFUL_MESSAGE)
                } else {
                    onSuccess()
                    Resource.Success()
                }
            }
        } catch (e: Exception) {
            exceptionError(e)
        }
    }
}