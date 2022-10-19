package com.martinlacorrona.helloarchitecture.repository.remote.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class UserRemoteEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("birthdate") val birthdate: Date
)