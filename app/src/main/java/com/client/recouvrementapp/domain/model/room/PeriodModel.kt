package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "TPeriod",
    indices = [Index(
        value = ["period_id"],
        unique = true
    )]
)
data class PeriodModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "period_id") val id : Int = 1,
    val name : String = ""
)