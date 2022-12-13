package com.martinlacorrona.helloarchitecture.data.local

import androidx.room.*
import com.martinlacorrona.helloarchitecture.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserEntity>)

    @Update
    fun updateUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE name LIKE :name")
    fun getAllByName(name: String): Flow<List<UserEntity>>

    @Query("DELETE FROM user WHERE remote_id=:remoteId")
    fun deleteUser(remoteId: Int)

    @Query("DELETE FROM user")
    fun deleteAll()
}