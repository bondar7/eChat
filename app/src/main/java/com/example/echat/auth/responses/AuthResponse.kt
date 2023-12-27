package com.example.echat.auth.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val username: String,
    val email: String,
    val bio: String,
    val token: String
)