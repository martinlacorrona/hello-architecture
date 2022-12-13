package com.martinlacorrona.helloarchitecture.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}