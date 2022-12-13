package com.martinlacorrona.helloarchitecture.data.mapper

import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.data.remote.model.UserDto
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import java.util.*

fun UserEntity.toUserModel() =
    UserModel(
        id ?: 0,
        remoteId ?: 0,
        name ?: "",
        birthday ?: 0
    )

fun UserModel.toUserEntity() =
    UserEntity(
        id = id,
        remoteId = remoteId,
        name = name,
        birthday = birthday
    )

fun UserModel.toUserDto() =
    UserDto(
        id = 0,
        name = name,
        birthdate = Date(birthday)
    )