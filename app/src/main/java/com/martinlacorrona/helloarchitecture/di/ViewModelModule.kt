package com.martinlacorrona.helloarchitecture.di

import com.martinlacorrona.helloarchitecture.presentation.viewmodel.CreateUserViewModel
import com.martinlacorrona.helloarchitecture.presentation.viewmodel.EditUserViewModel
import com.martinlacorrona.helloarchitecture.presentation.viewmodel.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        UserListViewModel(get(), get(), get())
    }
    viewModel {
        EditUserViewModel(get(), get(), get())
    }
    viewModel {
        CreateUserViewModel(get(), get())
    }
}