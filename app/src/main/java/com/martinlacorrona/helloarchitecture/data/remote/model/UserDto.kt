package com.martinlacorrona.helloarchitecture.data.remote.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("birthdate") val birthdate: Date
)