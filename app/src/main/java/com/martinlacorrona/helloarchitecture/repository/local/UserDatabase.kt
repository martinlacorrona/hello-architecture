package com.martinlacorrona.helloarchitecture.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.martinlacorrona.helloarchitecture.repository.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}