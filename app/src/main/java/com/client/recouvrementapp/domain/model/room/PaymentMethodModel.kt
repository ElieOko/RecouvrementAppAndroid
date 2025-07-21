package com.client.recouvrementapp.domain.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PaymentMethodModel(
    @PrimaryKey val uid: Int,
    val id : Int = 0,
    val name : String = ""
)