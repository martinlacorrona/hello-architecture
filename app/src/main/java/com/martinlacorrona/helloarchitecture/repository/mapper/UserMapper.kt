package com.martinlacorrona.helloarchitecture.repository.mapper

import com.martinlacorrona.helloarchitecture.repository.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.repository.remote.model.UserRemoteEntity
import java.util.*

object UserMapper {
    fun mapResponseToEntity(userRemoteEntity: UserRemoteEntity): UserEntity {
        return UserEntity(
            remoteId = userRemoteEntity.id,
            name = userRemoteEntity.name,
            birthday = userRemoteEntity.birthdate.time
        )
    }

    fun mapEntityToResponse(userEntity: UserEntity): UserRemoteEntity {
        return UserRemoteEntity(
            id = userEntity.remoteId ?: 0,
            name = userEntity.name ?: "",
            birthdate = Date(userEntity.birthday ?: 0)
        )
    }
}