package com.martinlacorrona.helloarchitecture.di

import com.martinlacorrona.helloarchitecture.domain.usecase.*
import com.martinlacorrona.helloarchitecture.domain.usecase.impl.*
import org.koin.dsl.module

val useCaseModule = module {
    single<GetUserListUseCase> {
        return@single GetUserListUseCaseImpl(get())
    }
    single<FetchUserListUseCase> {
        return@single FetchUserListUseCaseImpl(get())
    }
    single<IsFetchingUserListUseCase> {
        return@single IsFetchingUserListUseCaseImpl(get())
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