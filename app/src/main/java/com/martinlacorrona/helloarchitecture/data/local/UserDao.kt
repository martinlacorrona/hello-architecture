package com.martinlacorrona.helloarchitecture.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE name LIKE :name")
    fun getAllByName(name: String): LiveData<List<UserEntity>>

    @Query("DELETE FROM user WHERE remote_id=:remoteId")
    suspend fun deleteUser(remoteId: Int)

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}