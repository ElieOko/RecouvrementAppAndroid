package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "TCurrency"
)
data class CurrencyModel(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "currency_id") val id : Int = 0,
    val name : String = "",
    val code : String = "",
    val symbole : String = ""
)