package com.martinlacorrona.helloarchitecture.di

import com.martinlacorrona.helloarchitecture.ui.viewmodel.CreateUserViewModel
import com.martinlacorrona.helloarchitecture.ui.viewmodel.EditUserViewModel
import com.martinlacorrona.helloarchitecture.ui.viewmodel.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        UserListViewModel(get(), get())
    }
    viewModel {
        EditUserViewModel(get(), get())
    }
    viewModel {
        CreateUserViewModel(get())
    }
}