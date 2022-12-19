package com.martinlacorrona.helloarchitecture.domain.repository

import com.martinlacorrona.helloarchitecture.domain.util.Resource

interface BaseRepository {

    fun exceptionError(e: Exception) =
        Resource.Error<Unit>(e.message ?: UNKNOWN_ERROR_MESSAGE)

    companion object {
       const val RESPONSE_NOT_SUCCESSFUL_MESSAGE = "Response not successful."
       const val UNKNOWN_ERROR_MESSAGE = "An unknown error occurred."
    }
}