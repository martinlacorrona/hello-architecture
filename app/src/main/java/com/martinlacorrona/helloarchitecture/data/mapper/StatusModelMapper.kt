package com.martinlacorrona.helloarchitecture.data.mapper

import com.martinlacorrona.helloarchitecture.data.util.Status
import com.martinlacorrona.helloarchitecture.domain.model.StatusModel

fun Status.toStatusModel(): StatusModel =
    when (this) {
        Status.LOADING -> StatusModel.LOADING
        Status.SUCCESS -> StatusModel.SUCCESS
        Status.ERROR -> StatusModel.ERROR
    }