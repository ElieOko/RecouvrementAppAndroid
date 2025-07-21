package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PeriodModel(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "period_id") val id : Int = 1,
    val name : String = ""
)