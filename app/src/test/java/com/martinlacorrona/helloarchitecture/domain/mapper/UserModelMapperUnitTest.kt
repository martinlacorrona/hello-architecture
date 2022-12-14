package com.martinlacorrona.helloarchitecture.domain.mapper

import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.data.mapper.toUserDto
import com.martinlacorrona.helloarchitecture.data.mapper.toUserEntity
import com.martinlacorrona.helloarchitecture.data.mapper.toUserModel
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import org.junit.Assert.assertEquals
import org.junit.Test

class UserModelMapperUnitTest {

    private val dummyUserModel = UserModel(1, 11, "testName", 1)
    private val dummyUserEntity = UserEntity(1, 11, "testName", 1)

    @Test
    fun testMapToUserModel() {
        val userModelMapped = dummyUserEntity.toUserModel()
        assertEquals(userModelMapped.id, dummyUserEntity.id)
        assertEquals(userModelMapped.remoteId, dummyUserEntity.remoteId)
        assertEquals(userModelMapped.name, dummyUserEntity.name)
        assertEquals(userModelMapped.birthday, dummyUserEntity.birthday)
    }

    @Test
    fun testMapToUserEntity() {
        val userModelMapped = dummyUserModel.toUserEntity()
        assertEquals(userModelMapped.id, dummyUserModel.id)
        assertEquals(userModelMapped.remoteId, dummyUserModel.remoteId)
        assertEquals(userModelMapped.name, dummyUserModel.name)
        assertEquals(userModelMapped.birthday, dummyUserModel.birthday)
    }

    @Test
    fun testMapToNewRemoteEntity() {
        val userModelMapped = dummyUserModel.toUserDto()
        assertEquals(userModelMapped.id, 0)
        assertEquals(userModelMapped.name, dummyUserModel.name)
        assertEquals(userModelMapped.birthdate.time, dummyUserModel.birthday)
    }
}