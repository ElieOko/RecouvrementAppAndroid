package com.client.recouvrementapp.domain.model.core.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id : Int,
    val displayName : String,
    val username : String
)


@Serializable
data class ProfilUser(
    val token_type      : String,
    val access_token    : String,
    val profile         : User
)