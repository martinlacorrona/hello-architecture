package com.martinlacorrona.helloarchitecture.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.martinlacorrona.helloarchitecture.data.local.UserDao
import com.martinlacorrona.helloarchitecture.data.mapper.toUserEntity
import com.martinlacorrona.helloarchitecture.data.mapper.toUserModel
import com.martinlacorrona.helloarchitecture.data.remote.UserListRemote
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.repository.BaseRepository.Companion.RESPONSE_NOT_SUCCESSFUL_MESSAGE
import com.martinlacorrona.helloarchitecture.domain.repository.BaseRepository.Companion.UNKNOWN_ERROR_MESSAGE
import com.martinlacorrona.helloarchitecture.domain.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserListRepositoryImpl(
    private val userDao: UserDao,
    private val userListRemote: UserListRemote,
    private val databaseDispatcher: CoroutineDispatcher
) : UserListRepository {

    private val isFetchingUserList = MutableLiveData<Boolean>().apply { value = false }

    override suspend fun fetchUserList(): Resource<Unit> {
        return try {
            isFetchingUserList.postValue(true)
            val response = userListRemote.getUsers()
            withContext(databaseDispatcher) {
                if (!response.isSuccessful || response.body() == null)
                    Resource.Error(RESPONSE_NOT_SUCCESSFUL_MESSAGE)
                else {
                    userDao.deleteAll()
                    userDao.insertAll(response.body()!!.map { it.toUserEntity() })
                    isFetchingUserList.postValue(false)
                    Resource.Success()
                }
            }
        } catch (e: Exception) {
            isFetchingUserList.postValue(false)
            Resource.Error(e.message ?: UNKNOWN_ERROR_MESSAGE)
        }
    }

    override fun isFetching(): LiveData<Boolean> = isFetchingUserList

    override fun getUserList(name: String): LiveData<List<UserModel>> =
        Transformations.map(userDao.getAllByName("$name%")) {
            it.map { userEntity -> userEntity.toUserModel() }
        }
}