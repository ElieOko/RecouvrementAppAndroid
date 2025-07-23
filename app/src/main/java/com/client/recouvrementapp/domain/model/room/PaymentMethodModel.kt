package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.client.recouvrementapp.domain.model.DataSelect

@Entity(
    tableName = "TPaymentMethod",
    indices = [Index(
        value = ["payment_method_id"],
        unique = true
    )]
)
data class PaymentMethodModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "payment_method_id") val id : Int = 0,
    val name : String = ""
){
    fun asDataSelect(item: List<PaymentMethodModel>?) : List<DataSelect>{
        val listDataSelect = mutableListOf<DataSelect>()
        item?.forEach {
            listDataSelect.add(DataSelect(id = it.id, name = it.name))
        }
        return listDataSelect
    }
}