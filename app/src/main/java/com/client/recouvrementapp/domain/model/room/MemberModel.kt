package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "TMember",
    indices = [Index(
        value = ["member_id"],
        unique = true
    )]
)
data class MemberModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "member_id") val id : Int = 1,
    val name : String = ""
)