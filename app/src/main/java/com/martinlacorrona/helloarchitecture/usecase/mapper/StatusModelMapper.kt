package com.martinlacorrona.helloarchitecture.usecase.mapper

import com.martinlacorrona.helloarchitecture.repository.util.Status
import com.martinlacorrona.helloarchitecture.ui.model.StatusModel

object StatusModelMapper {
    fun mapToStatusModel(status: Status): StatusModel =
        when (status) {
            Status.LOADING -> StatusModel.LOADING
            Status.SUCCESS -> StatusModel.SUCCESS
            Status.ERROR -> StatusModel.ERROR
        }
}