package com.martinlacorrona.helloarchitecture.repository.mapper

import com.martinlacorrona.helloarchitecture.repository.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.repository.remote.model.UserRemoteEntity
import org.junit.Test
import java.util.*

class UserMapperUnitTest {

    private val dummyUserEntityModel = UserRemoteEntity(1, "testName", Date(1))
    private val dummyUserEntity = UserEntity(1, 11, "testName", 1)

    @Test
    fun testMapResponseToEntity() {
        val userModelMapped = UserMapper.mapResponseToEntity(dummyUserEntityModel)
        assert(userModelMapped.remoteId == dummyUserEntityModel.id)
        assert(userModelMapped.name == dummyUserEntityModel.name)
        assert(userModelMapped.birthday == dummyUserEntityModel.birthdate.time)
    }

    @Test
    fun testMapEntityToResponse() {
        val userModelMapped = UserMapper.mapEntityToResponse(dummyUserEntity)
        assert(userModelMapped.id == dummyUserEntity.remoteId)
        assert(userModelMapped.name == dummyUserEntity.name)
        assert(userModelMapped.birthdate.time == dummyUserEntity.birthday)
    }
}