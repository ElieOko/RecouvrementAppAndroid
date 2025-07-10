package com.client.recouvrementapp.domain.model

import com.client.recouvrementapp.domain.model.core.Currency

data class RecouvrementAmountOfDay(
    val currency: Currency,
    val amount : Double
)
