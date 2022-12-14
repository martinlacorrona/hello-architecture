package com.martinlacorrona.helloarchitecture.domain.repository

interface BaseRepository {
    companion object {
       const val RESPONSE_NOT_SUCCESSFUL_MESSAGE = "Response not successful."
       const val UNKNOWN_ERROR_MESSAGE = "An unknown error occurred."
    }
}