package com.martinlacorrona.helloarchitecture.di

import android.app.Application
import androidx.room.Room
import com.martinlacorrona.helloarchitecture.BuildConfig
import com.martinlacorrona.helloarchitecture.repository.UserListRepository
import com.martinlacorrona.helloarchitecture.repository.UserRepository
import com.martinlacorrona.helloarchitecture.repository.impl.UserListRepositoryImpl
import com.martinlacorrona.helloarchitecture.repository.impl.UserRepositoryImpl
import com.martinlacorrona.helloarchitecture.repository.local.UserDao
import com.martinlacorrona.helloarchitecture.repository.local.UserDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single { provideDatabase(androidApplication()) }

    //Dispatchers
    single(named("database")) { Dispatchers.IO }

    //Dao
    single { provideUserDao(get()) }

    //Repositories
    single<UserListRepository> {
        return@single UserListRepositoryImpl(get(), get(), get(named("database")))
    }
    single<UserRepository> {
        return@single UserRepositoryImpl(get(), get(), get(named("database")))
    }
}

fun provideDatabase(application: Application): UserDatabase {
    return Room.databaseBuilder(
        application, UserDatabase::class.java, BuildConfig.DATABASE_NAME
    ).build()
}

fun provideUserDao(dataBase: UserDatabase): UserDao {
    return dataBase.userDao
}