package com.client.recouvrementapp.domain.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PeriodModel(
    @PrimaryKey val uid: Int,
    val id : Int = 1,
    val name : String = ""
)