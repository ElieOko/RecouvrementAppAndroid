package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.client.recouvrementapp.domain.model.DataSelect
import kotlin.collections.forEach


@Entity(
    tableName = "TCurrency",
    indices = [Index(
        value = ["currency_id"],
        unique = true
    )]
)
data class CurrencyModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "currency_id")
    val id : Int = 0,
    val name : String = "",
    val code : String = "",
    val symbole : String = ""
){
    fun asDataSelect(item: List<CurrencyModel>?) : List<DataSelect>{
        val listDataSelect = mutableListOf<DataSelect>()
        item?.forEach {
            listDataSelect.add(DataSelect(id = it.id, name = "${it.symbole} (${it.code})"))
        }
        return listDataSelect
    }
}