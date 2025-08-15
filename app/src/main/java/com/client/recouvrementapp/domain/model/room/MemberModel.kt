package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.client.recouvrementapp.domain.model.DataSelect

@Entity(
    tableName = "TMembre",
    indices = [Index(
        value = ["membre_id"],
        unique = true
    )]
)
data class MembreModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "membre_id") val id : Int = 1,
    val name : String = ""
)