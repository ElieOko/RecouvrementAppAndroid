package com.client.recouvrementapp.domain.model.room

import androidx.room.Embedded
import androidx.room.Relation


data class RecouvrementWithRelations(

    @Embedded
    val recouvrement: RecouvrementModel?=null,

    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id"
    )
    val user: UserModel?=null,

    @Relation(
        parentColumn = "payment_method_id",
        entityColumn = "payment_method_id"
    )
    val paymentMethod: PaymentMethodModel?=null,

    @Relation(
        parentColumn = "currency_id",
        entityColumn = "currency_id"
    )
    val currency: CurrencyModel?=null,

    @Relation(
        parentColumn = "period_id",
        entityColumn = "period_id"
    )
    val period: PeriodModel?=null,
)
