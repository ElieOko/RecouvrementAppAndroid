package com.client.recouvrementapp.domain.model.core

import com.client.recouvrementapp.domain.model.DataSelect
import kotlinx.serialization.Serializable

@Serializable
data class TransactionType(
    val id : Int = 0,
    val name: String = "",
    val description : String = ""
){
    fun listTransactionType() : List<TransactionType>{
        return listOf(
            TransactionType(10, "Subscription","Subscription"),
            TransactionType(11, "LoanRepay", "Prêt"),
            TransactionType(12, "CotisationOrdinaire", "Cotisation Ordinaire"),
            TransactionType(13, "CotisationSpesm","Cotisation Spesm"),
            TransactionType(14, "Savings", "Epargne"),
        )
    }

    fun asDataSelect(item : List<TransactionType> = listTransactionType()) : List<DataSelect>{
         val listDataSelect = mutableListOf<DataSelect>()
        item.forEach {
            listDataSelect.add(DataSelect(id = it.id, name = it.description))
        }
        return listDataSelect
    }
}
