package com.martinlacorrona.helloarchitecture.repository.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user", indices = [Index(value = ["remote_id"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "remote_id") val remoteId: Int? = null,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "birthday") val birthday: Long?
)