package com.martinlacorrona.helloarchitecture.data.mapper

import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.data.remote.model.UserDto
import org.junit.Test
import java.util.*

class UserMapperUnitTest {

    private val dummyUserEntityModel = UserDto(1, "testName", Date(1))
    private val dummyUserEntity = UserEntity(1, 11, "testName", 1)

    @Test
    fun testMapResponseToEntity() {
        val userModelMapped = dummyUserEntityModel.toUserEntity()
        assert(userModelMapped.remoteId == dummyUserEntityModel.id)
        assert(userModelMapped.name == dummyUserEntityModel.name)
        assert(userModelMapped.birthday == dummyUserEntityModel.birthdate.time)
    }

    @Test
    fun testMapEntityToDto() {
        val userModelMapped = dummyUserEntity.toUserDto()
        assert(userModelMapped.id == dummyUserEntity.remoteId)
        assert(userModelMapped.name == dummyUserEntity.name)
        assert(userModelMapped.birthdate.time == dummyUserEntity.birthday)
    }
}