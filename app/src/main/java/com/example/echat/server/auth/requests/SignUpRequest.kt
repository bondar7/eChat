package com.example.echat.server.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val username: String,
    val email: String,
    val password: String
)