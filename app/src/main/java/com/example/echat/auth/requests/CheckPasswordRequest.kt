package com.example.echat.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class CheckPasswordRequest(
    val usernameToFindUser: String,
    val password: String
)
