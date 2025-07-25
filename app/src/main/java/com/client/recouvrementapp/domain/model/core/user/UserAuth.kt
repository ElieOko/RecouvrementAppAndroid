package com.client.recouvrementapp.domain.model.core.user

import kotlinx.serialization.Serializable


@Serializable
data class UserAuth(
    val username : String,
    val password : String,
    val grant_type : String = "password"
)
