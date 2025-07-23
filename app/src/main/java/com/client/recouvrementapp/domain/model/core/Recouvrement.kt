package com.client.recouvrementapp.domain.model.core

import kotlinx.serialization.Serializable

@Serializable
data class Recouvrement(
    val transactionType : String,
    val communication   : String,
    val amount          : Int,
    val currencyId      : Int,
    val paymentMethodId : Int,
    val periodId        : Int? = null
)
