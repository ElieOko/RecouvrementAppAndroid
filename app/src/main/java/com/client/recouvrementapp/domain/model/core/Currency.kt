package com.client.recouvrementapp.domain.model.core

import com.client.recouvrementapp.domain.model.DataSelect

data class Currency(
    val id : Int = 0,
    val name : String = "",
    val code : String = "",
    val symbole : String = ""
){
    fun listCurrency() : List<Currency>{
        return listOf(
            Currency(1,"Dollar", "USD","$"),
            Currency(1,"Franc Congolais", "CDF","FC")
        )
    }

    fun asDataSelect(item : List<Currency> = listCurrency()) : List<DataSelect>{
        val listDataSelect = mutableListOf<DataSelect>()
        item.forEach {
            listDataSelect.add(DataSelect(id = it.id, name = "${it.symbole} (${it.code})"))
        }
        return listDataSelect
    }
}
