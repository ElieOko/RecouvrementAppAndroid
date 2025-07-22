package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "TPaymentMethod"
)
data class PaymentMethodModel(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "payment_method_id") val id : Int = 0,
    val name : String = ""
)