package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "TUser",
    indices = [Index(
        value = ["user_id"],
        unique = true
    )]
)
data class UserModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_id") val id: Int,
    @ColumnInfo(name = "display_name") val displayName: String?,
    @ColumnInfo(name = "username") val username: String?,
)
