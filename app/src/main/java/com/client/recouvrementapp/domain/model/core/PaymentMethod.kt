package com.client.recouvrementapp.domain.model.core

import com.client.recouvrementapp.domain.model.DataSelect

data class PaymentMethod(
    val id : Int = 0,
    val name : String = ""
){
    fun listPaymentMethod() : List<PaymentMethod>{
        return listOf(
            PaymentMethod(id = 1, name = "Paiements par carte bancaire"),
            PaymentMethod(id = 2, name = "Paiement en espèces"),
            PaymentMethod(id = 3, name = "Paiements mobiles")
        )
    }

    fun asDataSelect(item : List<PaymentMethod> = listPaymentMethod()) : List<DataSelect>{
        val listDataSelect = mutableListOf<DataSelect>()
        item.forEach {
            listDataSelect.add(DataSelect(id = it.id, name = it.name))
        }
        return listDataSelect
    }
}
