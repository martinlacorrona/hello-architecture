package com.martinlacorrona.helloarchitecture.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.martinlacorrona.helloarchitecture.data.local.UserDao
import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.data.mapper.toUserDto
import com.martinlacorrona.helloarchitecture.data.mapper.toUserEntity
import com.martinlacorrona.helloarchitecture.data.remote.UserListRemote
import com.martinlacorrona.helloarchitecture.data.remote.UserRemote
import com.martinlacorrona.helloarchitecture.data.remote.model.UserDto
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.domain.repository.BaseRepository
import com.martinlacorrona.helloarchitecture.domain.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.domain.repository.UserRepository
import com.martinlacorrona.helloarchitecture.domain.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.util.*

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

    @RelaxedMockK
    private lateinit var userRemote: UserRemote

    private lateinit var userRepository: UserRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val userDao = UserDaoTestImpl()

    private val dispatcher = Dispatchers.Unconfined

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        userRepository = UserRepositoryImpl(userDao, userRemote, dispatcher)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `create remote success`() = runBlocking {
        //Given
        val userModel = UserModel()
        coEvery { userRemote.createUser(userModel.toUserDto()) } returns Response.success(null)

        //When
        val result = userRepository.createUser(userModel)

        //Then
        assert(result is Resource.Success)
        assertEquals(0, userDao.getAllByName("").first().size) //it should not be added
    }

    @Test
    fun `create remote not success`() = runBlocking {
        //Given
        val userModel = UserModel()
        coEvery { userRemote.createUser(userModel.toUserDto()) } returns Response.error(400, "".toResponseBody())

        //When
        val result = userRepository.createUser(userModel)

        //Then
        assert(result is Resource.Error)
        assertEquals(BaseRepository.RESPONSE_NOT_SUCCESSFUL_MESSAGE, result.message)
        assertEquals(0, userDao.getAllByName("").first().size) //it should not be added
    }

    @Test
    fun `create user remote unknown exception`() = runBlocking {
        //Given
        val userModel = UserModel()
        coEvery { userRemote.createUser(userModel.toUserDto()) } throws Exception()

        //When
        val result = userRepository.createUser(userModel)

        //Then
        assert(result is Resource.Error)
        assertEquals(BaseRepository.UNKNOWN_ERROR_MESSAGE, result.message)
    }

    @Test
    fun `update remote success`() = runBlocking {
        //Given
        val userModel = UserModel()
        coEvery { userRemote.updateUser(userModel.toUserDto()) } returns Response.success(null)

        //When
        val result = userRepository.updateUser(userModel)

        //Then
        assert(result is Resource.Success)
        assertEquals(1, userDao.getAllByName("").first().size)
        assertEquals(userModel.toUserEntity(), userDao.getAllByName("").first()[0])
    }

    @Test
    fun `remove remote success`() = runBlocking {
        //Given
        val userModelRemoteId = 1
        val userModelToDelete = UserModel(remoteId = 1)
        val userModelToStay = UserModel(remoteId = 2)
        coEvery { userRemote.deleteUser(userModelRemoteId) } returns Response.success(null)

        //When
        userDao.insertAll(listOf(userModelToDelete.toUserEntity(), userModelToStay.toUserEntity()))
        val result = userRepository.removeUser(userModelToDelete.remoteId)

        //Then
        assert(result is Resource.Success)
        assertEquals(1, userDao.getAllByName("").first().size)
        assertEquals(userModelToStay.toUserEntity(), userDao.getAllByName("").first()[0])
    }

    class UserDaoTestImpl: UserDao {
        private val data: ArrayList<UserEntity> = arrayListOf()

        override suspend fun insertAll(users: List<UserEntity>) {
            data.addAll(users)
        }

        override suspend fun updateUser(user: UserEntity) {
            data.add(user)
        }

        override fun getAllByName(name: String): Flow<List<UserEntity>> {
            return flowOf(data)
        }

        override suspend fun deleteUser(remoteId: Int) {
            data.remove(data.find { userEntity -> remoteId == userEntity.remoteId })
        }

        override suspend fun deleteAll() {
            data.clear()
        }
    }

}