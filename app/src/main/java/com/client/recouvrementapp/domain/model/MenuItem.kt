package com.client.recouvrementapp.domain.model

data class MenuItem(
    val Id : Int = 0,
    val name : String = "",
    val eventClick : ()-> Unit
)
