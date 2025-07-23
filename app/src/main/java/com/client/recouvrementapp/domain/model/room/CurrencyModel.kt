package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "TCurrency",
    indices = [Index(
        value = ["currency_id"],
        unique = true
    )]
)
data class CurrencyModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "currency_id")
    val id : Int = 0,
    val name : String = "",
    val code : String = "",
    val symbole : String = ""
)