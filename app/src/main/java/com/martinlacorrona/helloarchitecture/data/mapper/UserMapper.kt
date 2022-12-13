package com.martinlacorrona.helloarchitecture.data.mapper

import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.data.remote.model.UserDto
import java.util.*

fun UserDto.toUserEntity() =
    UserEntity(
        remoteId = id,
        name = name,
        birthday = birthdate.time
    )

fun UserEntity.toUserDto() =
    UserDto(
        id = remoteId ?: 0,
        name = name ?: "",
        birthdate = Date(birthday ?: 0)
    )