package com.client.recouvrementapp.domain.model.core

import com.client.recouvrementapp.domain.model.DataSelect
import kotlinx.serialization.Serializable

@Serializable
data class TransactionType(
    val id : Int = 0,
    val name: String = ""
){
    fun listTransactionType() : List<TransactionType>{
        return listOf(
            TransactionType(10, "Subscription"),
            TransactionType(11, "LoanRepay"),
            TransactionType(12, "Cotisation Ordinaire"),
            TransactionType(13, "Cotisation Spesm"),
            TransactionType(14, "Epargne"),
        )
    }

    fun asDataSelect(item : List<TransactionType> = listTransactionType()) : List<DataSelect>{
         val listDataSelect = mutableListOf<DataSelect>()
        item.forEach {
            listDataSelect.add(DataSelect(id = it.id, name = it.name))
        }
        return listDataSelect
    }
}
