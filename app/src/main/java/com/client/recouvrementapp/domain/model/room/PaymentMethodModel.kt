package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "TPaymentMethod",
    indices = [Index(
        value = ["payment_method_id"],
        unique = true
    )]
)
data class PaymentMethodModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "payment_method_id") val id : Int = 0,
    val name : String = ""
)