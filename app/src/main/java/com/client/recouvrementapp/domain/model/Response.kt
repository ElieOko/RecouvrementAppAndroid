package com.client.recouvrementapp.domain.model

import com.client.recouvrementapp.domain.model.core.user.User
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

@Serializable
data class ResponseHttpRequestPayment(
    val id : Int,
    val createdOn : String
)

@Serializable
data class KeyValue(
    val id : Int,
    val name : String
)