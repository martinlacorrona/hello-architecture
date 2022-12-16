package com.martinlacorrona.helloarchitecture.domain.usecase.impl

import androidx.lifecycle.MutableLiveData
import com.martinlacorrona.helloarchitecture.domain.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.domain.usecase.IsFetchingUserListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class IsFetchingUseCaseImplTest {

    @RelaxedMockK
    private lateinit var userListRepository: UserListRepository

    lateinit var isFetchingUseCase: IsFetchingUserListUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        isFetchingUseCase = IsFetchingUserListUseCaseImpl(userListRepository)
    }

    @Test
    fun `is fetching`() = runBlocking {
        //Given
        coEvery { userListRepository.isFetching() } returns MutableLiveData(true)

        //When
        val result = isFetchingUseCase.invoke()

        //Then
        assertEquals(true, result.value)
    }

    @Test
    fun `is not fetching`() = runBlocking {
        //Given
        coEvery { userListRepository.isFetching() } returns MutableLiveData(false)

        //When
        val result = isFetchingUseCase.invoke()

        //Then
        assertEquals(false, result.value)
    }
}