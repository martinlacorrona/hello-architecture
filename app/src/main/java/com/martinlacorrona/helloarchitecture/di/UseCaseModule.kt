package com.martinlacorrona.helloarchitecture.di

import com.martinlacorrona.helloarchitecture.usecase.*
import com.martinlacorrona.helloarchitecture.usecase.impl.*
import org.koin.dsl.module

val useCaseModule = module {
    single<GetUserListUseCase> {
        return@single GetUserListUseCaseImpl(get())
    }
    single<FetchUserListUseCase> {
        return@single FetchUserListUseCaseImpl(get())
    }

    single<CreateUserUseCase> {
        return@single CreateUserUseCaseImpl(get())
    }
    single<DeleteUserUseCase> {
        return@single DeleteUserUseCaseImpl(get())
    }
    single<EditUserUseCase> {
        return@single EditUserUseCaseImpl(get())
    }
}