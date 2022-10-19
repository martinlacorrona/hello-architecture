package com.martinlacorrona.helloarchitecture

import android.app.Application
import com.martinlacorrona.helloarchitecture.di.appModule
import com.martinlacorrona.helloarchitecture.di.repositoryModule
import com.martinlacorrona.helloarchitecture.di.useCaseModule
import com.martinlacorrona.helloarchitecture.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(appModule, repositoryModule, useCaseModule, viewModelModule))
        }
    }
}