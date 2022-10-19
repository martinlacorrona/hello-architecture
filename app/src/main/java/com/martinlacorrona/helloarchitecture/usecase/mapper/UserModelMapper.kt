package com.martinlacorrona.helloarchitecture.usecase.mapper

import com.martinlacorrona.helloarchitecture.repository.local.entity.UserEntity
import com.martinlacorrona.helloarchitecture.repository.remote.model.UserRemoteEntity
import com.martinlacorrona.helloarchitecture.ui.model.UserModel
import java.util.*

object UserModelMapper {
    fun mapToUserModel(userEntity: UserEntity): UserModel =
        UserModel(
            userEntity.id ?: 0,
            userEntity.remoteId ?: 0,
            userEntity.name ?: "",
            userEntity.birthday ?: 0
        )

    fun mapToUserEntity(userModel: UserModel): UserEntity =
        UserEntity(
            id = userModel.id,
            remoteId = userModel.remoteId,
            name = userModel.name,
            birthday = userModel.birthday
        )

    fun mapToNewRemoteEntity(userModel: UserModel): UserRemoteEntity =
        UserRemoteEntity(
            id = 0,
            name = userModel.name,
            birthdate = Date(userModel.birthday)
        )
}