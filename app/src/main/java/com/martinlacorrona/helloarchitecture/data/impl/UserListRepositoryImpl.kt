package com.martinlacorrona.helloarchitecture.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.martinlacorrona.helloarchitecture.domain.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.data.local.UserDao
import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.data.mapper.toUserEntity
import com.martinlacorrona.helloarchitecture.data.remote.UserListRemote
import com.martinlacorrona.helloarchitecture.data.util.Status
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext

class UserListRepositoryImpl(
    private val userDao: UserDao,
    private val userListRemote: UserListRemote,
    private val databaseDispatcher: CoroutineDispatcher
) : UserListRepository {

    private val isFetchingUserList = MutableLiveData<Boolean>().apply { value = false }

    override fun fetchUserList(): Flow<Status> = channelFlow {
        send(Status.LOADING)
        isFetchingUserList.postValue(true)
        try {
            val response = userListRemote.getUsers()
            withContext(databaseDispatcher) {
                if (!response.isSuccessful || response.body() == null) send(Status.ERROR)
                else {
                    userDao.deleteAll()
                    userDao.insertAll(response.body()!!.map { it.toUserEntity() })
                    send(Status.SUCCESS)
                    isFetchingUserList.postValue(false)
                }
            }
        } catch (e: Exception) {
            send(Status.ERROR)
            isFetchingUserList.postValue(false)
        }
    }

    override fun isFetching(): LiveData<Boolean> = isFetchingUserList

    override fun getUserList(name: String): Flow<List<UserEntity>> = userDao.getAllByName("$name%")

}