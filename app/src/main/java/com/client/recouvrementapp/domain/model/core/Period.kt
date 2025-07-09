package com.client.recouvrementapp.domain.model.core

import com.client.recouvrementapp.domain.model.DataSelect

data class Period(
    val id : Int = 1,
    val name : String = ""
){
    fun getListPeriod() : List<Period>{
        return listOf(
            Period(1, "Janvier 2025"),
            Period(2, "Febrier 2025"),
            Period(3, "Mars 2025"),
            Period(4, "Avril 2025"),
            Period(5, "Mai 2025"),
            Period(6, "Juin 2025"),
            Period(7, "Juillet 2025"),
        )
    }

    fun asDataSelect(item : List<Period> = getListPeriod()) : List<DataSelect>{
        val listDataSelect = mutableListOf<DataSelect>()
        item.forEach {
            listDataSelect.add(DataSelect(id = it.id, name = it.name))
        }
        return listDataSelect
    }
}
