package com.example.echat.server.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class CheckPasswordRequest(
    val usernameToFindUser: String,
    val password: String
)
