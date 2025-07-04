package com.client.recouvrementapp.domain.model

data class TypeRecouvrement(
    val id : Int,
    val title : String,
    var isActive : Boolean = false
)
