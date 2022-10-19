package com.martinlacorrona.helloarchitecture.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.martinlacorrona.helloarchitecture.BuildConfig
import com.martinlacorrona.helloarchitecture.repository.remote.UserListRemote
import com.martinlacorrona.helloarchitecture.repository.remote.UserRemote
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideOkHttpClient() }
    single { provideGson() }
    single { provideRetrofit(get(), get(), BuildConfig.API_URL) }
    single { provideUserListRemote(get()) }
    single { provideUserRemote(get()) }
}

private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
} else OkHttpClient
    .Builder()
    .build()

private fun provideGson() = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gson: Gson,
    baseUrl: String
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

private fun provideUserListRemote(retrofit: Retrofit): UserListRemote =
    retrofit.create(UserListRemote::class.java)

private fun provideUserRemote(retrofit: Retrofit): UserRemote =
    retrofit.create(UserRemote::class.java)