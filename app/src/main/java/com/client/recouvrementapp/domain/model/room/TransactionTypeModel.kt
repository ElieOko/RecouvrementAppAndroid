package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionTypeModel(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "transaction_type_id") val id : Int = 0,
    val name: String = ""
)
