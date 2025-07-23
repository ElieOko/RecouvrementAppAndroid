package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.client.recouvrementapp.domain.model.DataSelect
import kotlin.collections.forEach

@Entity(
    tableName = "TPeriod",
    indices = [Index(
        value = ["period_id"],
        unique = true
    )]
)
data class PeriodModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "period_id") val id : Int = 1,
    val name : String = ""
){
    fun asDataSelect(item: List<PeriodModel>?) : List<DataSelect>{
        val listDataSelect = mutableListOf<DataSelect>()
        item?.forEach {
            listDataSelect.add(DataSelect(id = it.id, name = it.name))
        }
        return listDataSelect
    }
}