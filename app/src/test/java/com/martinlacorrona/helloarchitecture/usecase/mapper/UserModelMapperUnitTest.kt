package com.martinlacorrona.helloarchitecture.usecase.mapper

import com.martinlacorrona.helloarchitecture.repository.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.ui.model.UserModel
import org.junit.Assert.assertEquals
import org.junit.Test

class UserModelMapperUnitTest {

    private val dummyUserModel = UserModel(1, 11, "testName", 1)
    private val dummyUserEntity = UserEntity(1, 11, "testName", 1)

    @Test
    fun testMapToUserModel() {
        val userModelMapped = UserModelMapper.mapToUserModel(dummyUserEntity)
        assertEquals(userModelMapped.id, dummyUserEntity.id)
        assertEquals(userModelMapped.remoteId, dummyUserEntity.remoteId)
        assertEquals(userModelMapped.name, dummyUserEntity.name)
        assertEquals(userModelMapped.birthday, dummyUserEntity.birthday)
    }

    @Test
    fun testMapToUserEntity() {
        val userModelMapped = UserModelMapper.mapToUserEntity(dummyUserModel)
        assertEquals(userModelMapped.id, dummyUserModel.id)
        assertEquals(userModelMapped.remoteId, dummyUserModel.remoteId)
        assertEquals(userModelMapped.name, dummyUserModel.name)
        assertEquals(userModelMapped.birthday, dummyUserModel.birthday)
    }

    @Test
    fun testMapToNewRemoteEntity() {
        val userModelMapped = UserModelMapper.mapToNewRemoteEntity(dummyUserModel)
        assertEquals(userModelMapped.id, 0)
        assertEquals(userModelMapped.name, dummyUserModel.name)
        assertEquals(userModelMapped.birthdate.time, dummyUserModel.birthday)
    }
}