package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserModel(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "user_id", ) val id: Int,
    @ColumnInfo(name = "display_name") val displayName: String?,
)
