package com.martinlacorrona.helloarchitecture.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.martinlacorrona.helloarchitecture.data.local.UserDao
import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.data.remote.UserListRemote
import com.martinlacorrona.helloarchitecture.data.remote.model.UserDto
import com.martinlacorrona.helloarchitecture.domain.repository.BaseRepository
import com.martinlacorrona.helloarchitecture.domain.repository.UserListRepository
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
class UserListRepositoryImplTest {

    @RelaxedMockK
    private lateinit var userDao: UserDao

    @RelaxedMockK
    private lateinit var userListRemote: UserListRemote

    private lateinit var userListRepository: UserListRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = Dispatchers.Unconfined

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch 2 users from remote`() = runBlocking {
        //Given
        userDao = UserDaoTestImpl()
        userListRepository = UserListRepositoryImpl(userDao, userListRemote, Dispatchers.Main)
        val name = "testName"
        val birthdate = Date(0)
        val listOfUsers = listOf(
                UserDto(id = 1, name = name, birthdate = birthdate),
                UserDto(id = 2, name = name, birthdate = birthdate),
            )

        coEvery { userListRemote.getUsers() } returns Response.success(listOfUsers)

        //When
        userListRepository.fetchUserList()
        val result = userListRepository.getUserList("").first()

        //Then
        assertEquals(2, result.size)
        assertEquals(name, result[0].name)
        assertEquals(0, result[0].birthday)
        assertEquals(false, userListRepository.isFetching().first())
    }

    @Test
    fun `fetch 0 users from remote`() = runBlocking {
        //Given
        userDao = UserDaoTestImpl()
        userListRepository = UserListRepositoryImpl(userDao, userListRemote, Dispatchers.Main)

        coEvery { userListRemote.getUsers() } returns Response.success(listOf())

        //When
        userListRepository.fetchUserList()
        val result = userListRepository.getUserList("").first()

        //Then
        assertEquals(0, result.size)
        assertEquals(false, userListRepository.isFetching().first())
    }

    @Test
    fun `fetch remote not successful`() = runBlocking {
        //Given
        userDao = UserDaoTestImpl()
        userListRepository = UserListRepositoryImpl(userDao, userListRemote, Dispatchers.Main)

        coEvery { userListRemote.getUsers() } returns Response.error(400, "".toResponseBody())

        //When
        val result = userListRepository.fetchUserList()

        //Then
        assert(result is Resource.Error)
        assertEquals(BaseRepository.RESPONSE_NOT_SUCCESSFUL_MESSAGE, result.message)
        assertEquals(false, userListRepository.isFetching().first())
    }

    @Test
    fun `fetch remote empty body`() = runBlocking {
        //Given
        userListRepository = UserListRepositoryImpl(userDao, userListRemote, Dispatchers.Main)

        coEvery { userListRemote.getUsers() } returns Response.success(null)

        //When
        val result = userListRepository.fetchUserList()

        //Then
        assert(result is Resource.Error)
        assertEquals(BaseRepository.RESPONSE_NOT_SUCCESSFUL_MESSAGE, result.message)
        assertEquals(false, userListRepository.isFetching().first())
    }

    @Test
    fun `fetch remote unknown exception`() = runBlocking {
        //Given
        userListRepository = UserListRepositoryImpl(userDao, userListRemote, Dispatchers.Main)

        coEvery { userListRemote.getUsers() } throws Exception()

        //When
        val result = userListRepository.fetchUserList()

        //Then
        assert(result is Resource.Error)
        assertEquals(BaseRepository.UNKNOWN_ERROR_MESSAGE, result.message)
        assertEquals(false, userListRepository.isFetching().first())
    }

    @Test
    fun `fetch remote message exception`() = runBlocking {
        //Given
        userListRepository = UserListRepositoryImpl(userDao, userListRemote, Dispatchers.Main)
        val exceptionMessage = "exceptionMessage"

        coEvery { userListRemote.getUsers() } throws Exception(exceptionMessage)

        //When
        val result = userListRepository.fetchUserList()

        //Then
        assert(result is Resource.Error)
        assertEquals(exceptionMessage, result.message)
        assertEquals(false, userListRepository.isFetching().first())
    }

    @Test
    fun `get user list`() = runBlocking {
        //Given
        userListRepository = UserListRepositoryImpl(userDao, userListRemote, Dispatchers.Main)
        val name = "testName"
        val birthday = 10L
        val listOfUsers = flowOf(
            listOf(
                UserEntity(id = 1, remoteId = 2, name = name, birthday = birthday),
                UserEntity(name = name, birthday = birthday),
                UserEntity(name = name, birthday = birthday),
            )
        )

        coEvery { userDao.getAllByName("$name%") } returns listOfUsers

        //When
        val result = userListRepository.getUserList(name).first()

        //Then
        assertEquals(3, result.size)
        assertEquals(name, result[0].name)
        assertEquals(birthday, result[0].birthday)
        assertEquals(false, userListRepository.isFetching().first())
    }

    class UserDaoTestImpl: UserDao {
        private val data: ArrayList<UserEntity> = arrayListOf()

        override suspend fun insertAll(users: List<UserEntity>) {
            data.addAll(users)
        }

        override suspend fun updateUser(user: UserEntity) {}

        override fun getAllByName(name: String): Flow<List<UserEntity>> {
            return flowOf(data)
        }

        override suspend fun deleteUser(remoteId: Int) {}

        override suspend fun deleteAll() {
            data.clear()
        }
    }

}