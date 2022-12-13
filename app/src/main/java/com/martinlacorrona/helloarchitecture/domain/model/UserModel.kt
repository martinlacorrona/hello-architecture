package com.martinlacorrona.helloarchitecture.domain.model

data class UserModel(
    val id: Int = 0,
    val remoteId: Int = 0,
    val name: String = "",
    val birthday: Long = 0
)