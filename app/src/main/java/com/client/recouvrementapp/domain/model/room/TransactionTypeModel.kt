package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "TTransactionType",
    indices = [Index(
        value = ["transaction_type_id"],
        unique = true
    )]
)
data class TransactionTypeModel(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "transaction_type_id") val id : Int = 0,
    val name: String = ""
)
