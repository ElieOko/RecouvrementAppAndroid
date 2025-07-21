package com.client.recouvrementapp.domain.model

import com.client.recouvrementapp.domain.model.user.User
import kotlinx.serialization.Serializable

@Serializable
data class ResponseHttpRequestAuth(
    val token_type      : String,
    val access_token    : String,
    val profile         : User
)

@Serializable
data class ResponseHttpRequest(
    val message : String?
)