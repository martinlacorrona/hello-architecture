package com.martinlacorrona.helloarchitecture.data.repository

import com.martinlacorrona.helloarchitecture.data.local.UserDao
import com.martinlacorrona.helloarchitecture.data.mapper.toUserEntity
import com.martinlacorrona.helloarchitecture.data.mapper.toUserModel
import com.martinlacorrona.helloarchitecture.data.remote.UserListRemote
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.repository.BaseRepository.Companion.RESPONSE_NOT_SUCCESSFUL_MESSAGE
import com.martinlacorrona.helloarchitecture.domain.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserListRepositoryImpl(
    private val userDao: UserDao,
    private val userListRemote: UserListRemote,
    private val databaseDispatcher: CoroutineDispatcher
) : UserListRepository {

    private val isFetchingUserList = MutableStateFlow(false)

    override suspend fun fetchUserList(): Resource<Unit> {
        return try {
            isFetchingUserList.value = true
            val response = userListRemote.getUsers()
            withContext(databaseDispatcher) {
                isFetchingUserList.value = false
                if (!response.isSuccessful || response.body() == null)
                    Resource.Error(RESPONSE_NOT_SUCCESSFUL_MESSAGE)
                else {
                    userDao.deleteAll()
                    userDao.insertAll(response.body()!!.map { it.toUserEntity() })
                    Resource.Success()
                }
            }
        } catch (e: Exception) {
            isFetchingUserList.value = false
            exceptionError(e)
        }
    }

    override fun isFetching(): Flow<Boolean> = isFetchingUserList

    override fun getUserList(name: String): Flow<List<UserModel>> =
        userDao.getAllByName("$name%")
            .map { it.map { userEntity -> userEntity.toUserModel() } }
}