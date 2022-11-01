package com.martinlacorrona.helloarchitecture.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.martinlacorrona.helloarchitecture.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.repository.local.UserDao
import com.martinlacorrona.helloarchitecture.repository.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.repository.mapper.UserMapper
import com.martinlacorrona.helloarchitecture.repository.remote.UserListRemote
import com.martinlacorrona.helloarchitecture.repository.util.Status
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
                    userDao.insertAll(response.body()!!.map { UserMapper.mapResponseToEntity(it) })
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